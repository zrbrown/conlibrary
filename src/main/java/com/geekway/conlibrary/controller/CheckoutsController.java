package com.geekway.conlibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Controller
@RequestMapping("/checkouts")
public class CheckoutsController {

    private static final DateTimeFormatter CHECK_OUT_IN_FORMAT = DateTimeFormatter.ofLocalizedDateTime(
            FormatStyle.FULL,
            FormatStyle.SHORT
    ).withZone(ZoneOffset.of("-06:00"));

    public record OldGame(String title) {
    }

    public record OldLibrary(String name) {
    }

    public record OldCopy(OldGame game, OldLibrary library) {
    }

    public record OldAttendee(String name, String badgeId) {
    }

    public record OldCheckout(OldCopy copy, OldAttendee attendee, Duration duration,
                              String start, String end, boolean playRegistered) {
    }

    public record Incidental(String gameTitle, String borrower) {
    }

    public record Checkin(String libraryId, String gameTitle, String borrower) {
    }

    public record GameCopy(long id, String title) {
    }

    public record Attendee(long id, String name, List<GameCopy> currentCheckouts) {
    }

    public record Checkout(GameCopy gameCopy, Attendee borrower) {
    }

    @GetMapping("/old/index")
    public String index(Model model) {
        LocalDateTime start = LocalDateTime.of(2023, 4, 5, 11, 23);
        LocalDateTime end = LocalDateTime.of(2023, 4, 5, 13, 30);
        model.addAttribute("checkouts", List.of(
                new OldCheckout(new OldCopy(new OldGame("Scythe"), new OldLibrary("Geekway 2023 General")),
                        new OldAttendee("Zack Brown", "1234567"),
                        Duration.between(start, end),
                        CHECK_OUT_IN_FORMAT.format(start),
                        CHECK_OUT_IN_FORMAT.format(end),
                        true)
        ));
        return "old/checkouts/index";
    }

    @GetMapping("/old/details")
    public String details(Model model) {
        LocalDateTime start = LocalDateTime.of(2023, 4, 5, 11, 23);
        LocalDateTime end = LocalDateTime.of(2023, 4, 5, 13, 30);
        model.addAttribute("checkout",
                new OldCheckout(new OldCopy(new OldGame("Scythe"), new OldLibrary("Geekway 2023 General")),
                        new OldAttendee("Zack Brown", "1234567"),
                        Duration.between(start, end),
                        CHECK_OUT_IN_FORMAT.format(start),
                        CHECK_OUT_IN_FORMAT.format(end),
                        true)
        );
        return "old/checkouts/details";
    }

    @GetMapping
    public String checkOutIn(Model model) {
        return "check_in_out/check_out_in";
    }

    @PostMapping("/barcode/game")
    public String barcodeGame(@RequestParam("gameScanInput") String gameScanInput, Model model) {
        return switch (gameScanInput) {
            case "5" -> {
                model.addAttribute("checkin", new Checkin("5", null, null));
                yield "check_in_out/check_out_in_game_not_found";
            }
            case "6" -> {
                model.addAttribute("checkin", new Checkin("6", null, null));
                model.addAttribute("incidental", new Incidental("Win, Lose, or Banana", "Brendon Faithfull"));
                yield "check_in_out/check_out_in_game_not_found";
            }
            case "8" -> {
                model.addAttribute("checkin", new Checkin("123", "7 Wonders", "Aaron Bruce"));
                yield "check_in_out/checkin_dialog";
            }
            case "9" -> {
                model.addAttribute("checkin", new Checkin("5678", "The Resistance", "Jared May"));
                model.addAttribute("incidental", new Incidental("Power Grid", "Zack Brown"));
                yield "check_in_out/checkin_dialog";
            }
            default -> {
                model.addAttribute("game", new OldGame("Hanabi"));
                yield "check_in_out/checkout_dialog";
            }
        };
    }

    @PostMapping("/barcode/attendee")
    public String barcodeAttendee(@RequestParam("badgeScanInput") String badgeScanInput, Model model) {
        return switch (badgeScanInput) {
            case "1" -> {
                model.addAttribute("attendeeId", badgeScanInput);
                model.addAttribute("incidentalGame", "Power Grid");
                yield "check_in_out/check_out_attendee_not_found";
            }
            case "2" -> {
                model.addAttribute("attendeeId", badgeScanInput);
                yield "check_in_out/check_out_attendee_not_found";
            }
            case "6" -> {
                model.addAttribute("checkout", new Checkout(
                        new GameCopy(1, "Power Grid"),
                        new Attendee(100, "Zack Brown", List.of(
                                new GameCopy(57, "Terraforming Mars"),
                                new GameCopy(67, "Zombie Dice")
                        ))));
                model.addAttribute("incidentalAttendee", "Nathan Vonder Haar");
                yield "check_in_out/check_out_confirm";
            }
            case "7" -> {
                model.addAttribute("checkout", new Checkout(
                        new GameCopy(1, "Power Grid"),
                        new Attendee(100, "Zack Brown", List.of())));
                model.addAttribute("incidentalAttendee", "Nathan Vonder Haar");
                yield "check_in_out/check_out_confirm";
            }
            case "8" -> {
                model.addAttribute("checkout", new Checkout(
                        new GameCopy(1, "Power Grid"),
                        new Attendee(100, "Zack Brown", List.of())));
                model.addAttribute("incidentalGame", "Hanabi");
                yield "check_in_out/check_out_confirm";
            }
            case "9" -> {
                model.addAttribute("checkout", new Checkout(
                        new GameCopy(1, "Power Grid"),
                        new Attendee(100, "Zack Brown", List.of())));
                model.addAttribute("incidentalAttendee", "Nathan Vonder Haar");
                model.addAttribute("incidentalGame", "Hanabi");
                yield "check_in_out/check_out_confirm";
            }
            default -> {
                model.addAttribute("checkout", new Checkout(
                        new GameCopy(1, "Power Grid"),
                        new Attendee(100, "Zack Brown", List.of())));
                yield "check_in_out/check_out_confirm";
            }
        };
    }
}
