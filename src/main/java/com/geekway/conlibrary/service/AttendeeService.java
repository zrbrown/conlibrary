package com.geekway.conlibrary.service;

import com.geekway.conlibrary.db.dto.AttendeeCheckoutDto;
import com.geekway.conlibrary.db.dto.AttendeeDetailDto;
import com.geekway.conlibrary.db.dto.AttendeePlayDto;
import com.geekway.conlibrary.db.dto.AttendeeSummaryDto;
import com.geekway.conlibrary.db.entity.Attendee;
import com.geekway.conlibrary.db.repository.AttendeeRepository;
import com.geekway.conlibrary.db.repository.CheckoutRepository;
import com.geekway.conlibrary.db.repository.PlayRepository;
import com.geekway.conlibrary.model.Activity;
import com.geekway.conlibrary.model.GameCopyMinimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional(readOnly = true)
@Service
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final CheckoutRepository checkoutRepository;
    private final PlayRepository playRepository;

    public AttendeeService(AttendeeRepository attendeeRepository, CheckoutRepository checkoutRepository,
                           PlayRepository playRepository) {
        this.attendeeRepository = attendeeRepository;
        this.checkoutRepository = checkoutRepository;
        this.playRepository = playRepository;
    }

    public List<AttendeeSummaryDto> getAttendees() {
        Page<AttendeeSummaryDto> attendees = attendeeRepository.getAll(PageRequest.of(0, 100, Sort.by(Sort.Direction.ASC, "firstName")));
        return attendees.toList();
    }

    public AttendeeDetailDto getAttendee(long attendeeId) {
        return attendeeRepository.findAttendee(attendeeId).orElseThrow();
    }

    @Transactional
    public AttendeeDetailDto updateAttendee(long attendeeId, String name, String surname,
                                            String pronouns, String badgeId) {
        Attendee attendee = attendeeRepository.findById(attendeeId).orElseThrow();
        attendee.setFirstName(name);
        attendee.setLastName(surname);
        attendee.setPronouns(pronouns);
        attendee.setBadgeId(badgeId);

        Attendee saved = attendeeRepository.save(attendee);

        return new AttendeeDetailDto(
                saved.getId(),
                saved.getFirstName(),
                saved.getLastName(),
                saved.getPronouns(),
                saved.getBadgeId()
        );
    }

    public List<Activity> getCheckoutActivity(long attendeeId) {
        //TODO virtual thread these
        List<AttendeeCheckoutDto> attendeeCheckouts = checkoutRepository.getAttendeeCheckouts(attendeeId);
        List<AttendeePlayDto> attendeePlays = playRepository.getAttendeeCheckouts(attendeeId);

        OffsetDateTime now = OffsetDateTime.now();
        List<Activity> checkoutActivities = attendeeCheckouts.stream().map(checkout -> {
            Function<AttendeeCheckoutDto, OffsetDateTime> endOrNowC = c -> c.end() == null ? now : c.end();
            Function<AttendeePlayDto, OffsetDateTime> endOrNowP = p -> p.end() == null ? now : p.end();
            List<GameCopyMinimal> overlapCheckouts = attendeeCheckouts.stream()
                    .filter(c -> c.checkoutId() == checkout.checkoutId() ||
                            (c.start().isAfter(checkout.start()) && c.start().isBefore(endOrNowC.apply(checkout))) ||
                            (endOrNowC.apply(c).isAfter(checkout.start()) && endOrNowC.apply(c).isBefore(endOrNowC.apply(checkout))))
                    .map(c -> new GameCopyMinimal(c.gameCopyId(), c.gameTitle()))
                    .toList();
            Map<GameCopyMinimal, Duration> overlapPlays = attendeePlays.stream()
                    .dropWhile(play -> play.end() != null && !play.end().isAfter(checkout.start()))//TODO sort behavior with null
                    .takeWhile(play -> play.start().isBefore(endOrNowC.apply(checkout)))
                    .collect(Collectors.toMap(
                            play -> new GameCopyMinimal(play.gameCopyId(), play.gameTitle()),
                            play -> Duration.between(play.start(), endOrNowP.apply(play))
                    ));
            return new Activity(checkout.start(), checkout.end(), overlapCheckouts, overlapPlays);
        }).toList();
        List<Activity> playActivities = attendeePlays.stream().map(play -> {
            Function<AttendeePlayDto, OffsetDateTime> endOrNowP = p -> p.end() == null ? now : p.end();
            Map<GameCopyMinimal, Duration> overlapPlays = attendeePlays.stream()
                    .filter(p -> p.checkoutId() == play.checkoutId() ||
                            (p.start().isAfter(play.start()) && p.start().isBefore(endOrNowP.apply(play))) ||
                            (endOrNowP.apply(p).isAfter(play.start()) && endOrNowP.apply(p).isBefore(endOrNowP.apply(play))))
                    .collect(Collectors.toMap(
                            p -> new GameCopyMinimal(p.gameCopyId(), p.gameTitle()),
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
        if (!nonEmptyActivities.isEmpty()) {
            activities.add(nonEmptyActivities.get(0));
        }
        //TODO add first empty if first activity start is after event check in

        return activities;
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
