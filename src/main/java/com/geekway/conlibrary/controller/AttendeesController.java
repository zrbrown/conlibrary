package com.geekway.conlibrary.controller;

import com.geekway.conlibrary.db.dto.AttendeeCheckoutDto;
import com.geekway.conlibrary.db.dto.AttendeeDetailDto;
import com.geekway.conlibrary.db.dto.AttendeePlayDto;
import com.geekway.conlibrary.db.dto.AttendeeSummaryDto;
import com.geekway.conlibrary.db.entity.Attendees;
import com.geekway.conlibrary.db.repository.AttendeeRepository;
import com.geekway.conlibrary.db.repository.CheckoutRepository;
import com.geekway.conlibrary.db.repository.PlayRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/attendees")
public class AttendeesController {

    private static final DateTimeFormatter CHECK_OUT_IN_FORMAT = DateTimeFormatter.ofLocalizedTime(
            FormatStyle.SHORT
    ).withZone(ZoneOffset.of("-06:00"));

    private final AttendeeRepository attendeeRepository;
    private final CheckoutRepository checkoutRepository;
    private final PlayRepository playRepository;

    public AttendeesController(AttendeeRepository attendeeRepository, CheckoutRepository checkoutRepository, PlayRepository playRepository) {
        this.attendeeRepository = attendeeRepository;
        this.checkoutRepository = checkoutRepository;
        this.playRepository = playRepository;
    }

    public record OldAttendee(String name, String badgeId) {
    }

    public record GameCopy(long id, String gameTitle) {
    }

    public record Activity(OffsetDateTime start, OffsetDateTime end, List<GameCopy> copyCheckouts,
                           Map<GameCopy, Duration> plays) {
        public Duration duration() {
            return Duration.between(start(), end() == null ? OffsetDateTime.now() : end());
        }

        public String startDisplay() {
            return start().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US) + " " + CHECK_OUT_IN_FORMAT.format(start());
        }

        public String endDisplay() {
            return (end().getDayOfWeek() == start().getDayOfWeek() ?
                    "" :
                    end().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US))
                    + " " + CHECK_OUT_IN_FORMAT.format(end());
        }
    }

    public record Attendee(long id, String name, String surname, String pronouns, String badgeId,
                           List<Activity> activities) {
    }

    public record BasicAttendee(long id, String name, String surname, String badgeId, List<GameCopy> currentCheckouts,
                                int checkoutCount,
                                int playCount) {
    }

    @GetMapping("/old/index")
    public String index(Model model) {
        model.addAttribute("attendees", List.of(
                new OldAttendee("Zack Brown", "1234567"),
                new OldAttendee("Nathan Vonder Haar", "9876543"))
        );
        return "old/attendees/index";
    }

    @GetMapping("/old/create")
    public String create(Model model) {
        return "old/attendees/create";
    }

    @GetMapping("/old/details")
    public String details(Model model) {
        model.addAttribute("attendee", new OldAttendee("Zack Brown", "1234567"));
        return "old/attendees/details";
    }

    @GetMapping("/old/edit")
    public String edit(Model model) {
        return "old/attendees/edit";
    }

    @GetMapping("/old/delete")
    public String delete(Model model) {
        model.addAttribute("attendee", new OldAttendee("Zack Brown", "1234567"));
        return "old/attendees/delete";
    }

    @GetMapping
    public String attendees(Model model) {
        Page<AttendeeSummaryDto> attendees = attendeeRepository.getAll(PageRequest.of(0, 100, Sort.by(Sort.Direction.ASC, "firstName")));
        model.addAttribute("attendees", attendees.toList());
        return "attendee/attendees";
    }

    @GetMapping("/{attendeeId}")
    public String attendee(@PathVariable("attendeeId") long borrowerId, Model model) {
        //TODO virtual thread these
        AttendeeDetailDto attendeeDetail = attendeeRepository.findAttendee(borrowerId).orElseThrow();
        List<AttendeeCheckoutDto> attendeeCheckouts = checkoutRepository.getAttendeeCheckouts(borrowerId);
        List<AttendeePlayDto> attendeePlays = playRepository.getAttendeeCheckouts(borrowerId);
        OffsetDateTime now = OffsetDateTime.now();

        List<Activity> checkoutActivities = attendeeCheckouts.stream().map(checkout -> {
            Function<AttendeeCheckoutDto, OffsetDateTime> endOrNowC = c -> c.end() == null ? now : c.end();
            Function<AttendeePlayDto, OffsetDateTime> endOrNowP = p -> p.end() == null ? now : p.end();
            List<GameCopy> overlapCheckouts = attendeeCheckouts.stream()
                    .filter(c -> c.checkoutId() == checkout.checkoutId() ||
                            (c.start().isAfter(checkout.start()) && c.start().isBefore(endOrNowC.apply(checkout))) ||
                            (endOrNowC.apply(c).isAfter(checkout.start()) && endOrNowC.apply(c).isBefore(endOrNowC.apply(checkout))))
                    .map(c -> new GameCopy(c.gameCopyId(), c.gameTitle()))
                    .toList();
            Map<GameCopy, Duration> overlapPlays = attendeePlays.stream()
                    .dropWhile(play -> play.end() != null && !play.end().isAfter(checkout.start()))//TODO sort behavior with null
                    .takeWhile(play -> play.start().isBefore(endOrNowC.apply(checkout)))
                    .collect(Collectors.toMap(
                            play -> new GameCopy(play.gameCopyId(), play.gameTitle()),
                            play -> Duration.between(play.start(), endOrNowP.apply(play))
                    ));
            return new Activity(checkout.start(), checkout.end(), overlapCheckouts, overlapPlays);
        }).toList();
        List<Activity> playActivities = attendeePlays.stream().map(play -> {
            Function<AttendeePlayDto, OffsetDateTime> endOrNowP = p -> p.end() == null ? now : p.end();
            Map<GameCopy, Duration> overlapPlays = attendeePlays.stream()
                    .filter(p -> p.checkoutId() == play.checkoutId() ||
                            (p.start().isAfter(play.start()) && p.start().isBefore(endOrNowP.apply(play))) ||
                            (endOrNowP.apply(p).isAfter(play.start()) && endOrNowP.apply(p).isBefore(endOrNowP.apply(play))))
                    .collect(Collectors.toMap(
                            p -> new GameCopy(p.gameCopyId(), p.gameTitle()),
                            p -> Duration.between(p.start(), endOrNowP.apply(p))
                    ));
            return new Activity(play.start(), play.end(), List.of(), overlapPlays);
        }).toList();
        List<Activity> nonEmptyActivities = Stream.concat(checkoutActivities.stream(), playActivities.stream())
                .filter(distinctByKey(activity -> Objects.hash(activity.start(), activity.end())))
                .sorted(Comparator.comparing(Activity::start))
                .toList();

        List<Activity> activities = new ArrayList<>();
        //TODO add last empty if last activity end is not null
        for (int i = nonEmptyActivities.size() - 1; i > 0; i--) {
            Activity activity = nonEmptyActivities.get(i);
            Activity previousActivity = nonEmptyActivities.get(i - 1);
            activities.add(activity);
            activities.add(new Activity(previousActivity.end() == null ? now : previousActivity.end(), activity.start(), List.of(), Map.of()));
        }
        activities.add(nonEmptyActivities.get(0));
        //TODO add first empty if first activity start is after event check in

        model.addAttribute("attendee", attendeeDetail);
        model.addAttribute("activities", activities);

        return "attendee/attendee_dialog";
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    @GetMapping("/{attendeeId}/edit")
    public String attendeeEdit(@PathVariable("attendeeId") int borrowerId, Model model) {
        AttendeeDetailDto attendee = attendeeRepository.findAttendee(borrowerId).orElseThrow();
        model.addAttribute("attendee", attendee);
        return "attendee/attendee_fields_edit";
    }

    @GetMapping("/{attendeeId}/cancel")
    public String attendeeCancel(@PathVariable("attendeeId") int borrowerId, Model model) {
        AttendeeDetailDto attendee = attendeeRepository.findAttendee(borrowerId).orElseThrow();
        model.addAttribute("attendee", attendee);
        return "attendee/attendee_fields";
    }

    @PatchMapping("/{attendeeId}")
    public String attendeeSave(@PathVariable("attendeeId") long borrowerId,
                               @RequestParam("name") String name,
                               @RequestParam("surname") String surname,
                               @RequestParam("pronouns") String pronouns,
                               @RequestParam("badgeId") String badgeId,
                               Model model) {
        Attendees attendee = attendeeRepository.findById(borrowerId).orElseThrow();
        attendee.setFirstName(name);
        attendee.setLastName(surname);
        attendee.setPronouns(pronouns);
        attendee.setBadgeId(badgeId);

        Attendees saved = attendeeRepository.save(attendee);
        model.addAttribute("attendee", new AttendeeDetailDto(
                saved.getId(),
                saved.getFirstName(),
                saved.getLastName(),
                saved.getPronouns(),
                saved.getBadgeId()
        ));
        return "attendee/attendee_fields";
    }

    @DeleteMapping("/{attendeeId}")
    public String attendeeDelete(@PathVariable("attendeeId") int borrowerId, Model model) {
        AttendeeDetailDto attendee = attendeeRepository.findAttendee(borrowerId).orElseThrow();
        model.addAttribute("attendee", attendee);
        return "attendee/attendee_delete_confirm_dialog";
    }
}

