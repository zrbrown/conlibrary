package com.geekway.conlibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/attendees")
public class AttendeesController {

    private static final DateTimeFormatter CHECK_OUT_IN_FORMAT = DateTimeFormatter.ofLocalizedTime(
            FormatStyle.SHORT
    ).withZone(ZoneOffset.of("-06:00"));

    public record OldAttendee(String name, String badgeId) {
    }

    public record GameCopy(long id, String gameTitle) {
    }

    public record Activity(LocalDateTime start, LocalDateTime end, List<GameCopy> copyCheckouts,
                           Map<GameCopy, Duration> plays) {
        public Duration duration() {
            return Duration.between(start(), end() == null ? LocalDateTime.now() : end());
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
        model.addAttribute("attendees", List.of(
                new BasicAttendee(1, "Zack", "Brown", "1234567", List.of(new GameCopy(1, "Power Grid")), 3, 4),
                new BasicAttendee(1, "Brendon", "Faithfull", "5678", List.of(new GameCopy(2, "Scythe"), new GameCopy(6, "Mansions of Madness 2nd Edition")), 10, 10),
                new BasicAttendee(1, "Robin", "Carroll-Dolci", "999999", List.of(), 0, 0),
                new BasicAttendee(2, "Nathan", "Vonder Haar", "9876543", List.of(), 0, 5)
        ));
        return "attendee/attendees";
    }

    @GetMapping("/{attendeeId}")
    public String attendee(@PathVariable("attendeeId") int borrowerId, Model model) {
        model.addAttribute("attendee", new Attendee(1, "Zack", "Brown", "he/him", "1234567", List.of(
                new Activity(LocalDateTime.of(2023, 4, 4, 15, 23, 34), LocalDateTime.of(2023, 4, 4, 20, 56, 0), List.of(
                        new GameCopy(1, "Power Grid")
                ), Map.of(
                        new GameCopy(1, "Power Grid"), Duration.ofHours(1)
                )),
                new Activity(LocalDateTime.of(2023, 4, 4, 11, 23, 34), LocalDateTime.of(2023, 4, 4, 13, 56, 0), List.of(
                ), Map.of(
                )),
                new Activity(LocalDateTime.of(2023, 4, 4, 9, 23, 34), LocalDateTime.of(2023, 4, 4, 10, 56, 0), List.of(
                        new GameCopy(2, "Scythe"), new GameCopy(6, "Mansions of Madness 2nd Edition")
                ), Map.of(
                        new GameCopy(2, "Scythe"), Duration.ofHours(1).plusMinutes(4),
                        new GameCopy(6, "Mansions of Madness 2nd Edition"), Duration.ofHours(1)
                )),
                new Activity(LocalDateTime.of(2023, 4, 3, 17, 23, 34), LocalDateTime.of(2023, 4, 4, 6, 56, 0), List.of(
                        new GameCopy(4, "Twilight Imperium")
                ), Map.of(
                        new GameCopy(4, "Twilight Imperium"), Duration.ofHours(10).plusMinutes(45),
                        new GameCopy(7, "Ticket to Ride"), Duration.ofHours(1)
                )),
                new Activity(LocalDateTime.of(2023, 4, 3, 10, 23, 34), LocalDateTime.of(2023, 4, 3, 12, 56, 0), List.of(
                        new GameCopy(5, "Carcassonne"),
                        new GameCopy(7, "Ticket to Ride")
                ), Map.of(
                        new GameCopy(5, "Carcassonne"), Duration.ofHours(1)
                )))
        ));
        return "attendee/attendee_dialog";
    }

    @GetMapping("/{attendeeId}/edit")
    public String attendeeEdit(@PathVariable("attendeeId") int borrowerId, Model model) {
        model.addAttribute("attendee", new Attendee(1, "Zack", "Brown", "he/him", "1234", List.of()));
        return "attendee/attendee_fields_edit";
    }

    @GetMapping("/{attendeeId}/cancel")
    public String attendeeCancel(@PathVariable("attendeeId") int borrowerId, Model model) {
        model.addAttribute("attendee", new Attendee(1, "Zack", "Brown", "he/him", "1234", List.of()));
        return "attendee/attendee_fields";
    }

    @PatchMapping("/{attendeeId}")
    public String attendeeSave(@PathVariable("attendeeId") int borrowerId,
                               @RequestParam("name") String name,
                               @RequestParam("surname") String surname,
                               @RequestParam("pronouns") String pronouns,
                               @RequestParam("badgeId") String badgeId,
                               Model model) {
        model.addAttribute("attendee", new Attendee(1, name, surname, pronouns, badgeId, List.of()));
        return "attendee/attendee_fields";
    }

    @DeleteMapping("/{attendeeId}")
    public String attendeeDelete(@PathVariable("attendeeId") int borrowerId, Model model) {
        model.addAttribute("attendee", new Attendee(1, "Zack", "Brown", "he/him", "1234", List.of()));
        return "attendee/attendee_delete_confirm_dialog";
    }
}

