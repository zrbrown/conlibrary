package com.geekway.conlibrary.controller;

import com.geekway.conlibrary.model.CheckoutStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/games")
public class GamesController {

    private static final DateTimeFormatter CHECK_OUT_IN_FORMAT = DateTimeFormatter.ofLocalizedTime(
            FormatStyle.SHORT
    ).withZone(ZoneOffset.of("-06:00"));

    public record Copy(String id) {
    }

    public record OldGame(long id, String title, List<Copy> copies, boolean checkedOut, CheckoutStatus checkoutStatus,
                          int playCount, int checkoutCount) {
    }

    public record Game(long id, String title, List<GameCopy> copies, CheckoutStatus checkoutStatus,
                       int playCount, int checkoutCount) {
    }

    public record Attendee(long id, String name, String badgeId) {
    }

    public record Checkout(long id, Attendee borrower, LocalDateTime start, LocalDateTime end,
                           Duration durationUntilNextCheckout) {
        public Duration duration() {
            return Duration.between(start(), end());
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

    public record GameCopy(long id, String libraryId, String library, String owner, String notes,
                           CheckoutStatus checkoutStatus,
                           LocalDateTime checkoutTime, Attendee borrower, List<Checkout> checkouts) {
        public String checkoutTimeDisplay() {
            return checkoutTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US) + " " + CHECK_OUT_IN_FORMAT.format(checkoutTime);
        }

        public Duration checkoutDuration() {
            return Duration.between(checkoutTime(), LocalDateTime.now());
        }
    }

    public static final List<Game> GAMES = List.of(
            new Game(111, "Power Grid", List.of(
                    new GameCopy(1467, "1467", "Common", "Zack Brown", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", CheckoutStatus.AVAILABLE, LocalDateTime.of(2023, 4, 5, 11, 23), null, List.of(
                            new Checkout(1, new Attendee(1, "Brendon Faithfull", "1234"), LocalDateTime.of(2023, 4, 4, 15, 23, 34), LocalDateTime.of(2023, 4, 4, 20, 56, 0), Duration.between(Instant.now().minusSeconds(800), Instant.now())),
                            new Checkout(1, new Attendee(1, "Zack Brown", "1234"), LocalDateTime.of(2023, 4, 4, 11, 23, 34), LocalDateTime.of(2023, 4, 4, 13, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())),
                            new Checkout(1, new Attendee(1, "Nathan Vonder Haar", "1234"), LocalDateTime.of(2023, 4, 4, 9, 23, 34), LocalDateTime.of(2023, 4, 4, 10, 56, 0), Duration.between(Instant.now().minusSeconds(46000), Instant.now())),
                            new Checkout(1, new Attendee(1, "Aaron Bruce", "1234"), LocalDateTime.of(2023, 4, 3, 17, 23, 34), LocalDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(16000), Instant.now())),
                            new Checkout(1, new Attendee(1, "Zack Brown", "1234"), LocalDateTime.of(2023, 4, 3, 10, 23, 34), LocalDateTime.of(2023, 4, 3, 12, 56, 0), Duration.between(Instant.now().minusSeconds(5000), Instant.now())))),
                    new GameCopy(4567, "4567", "Play and Win", "Geekway", "Donated by TMG", CheckoutStatus.EXTREME_OVERDUE, LocalDateTime.of(2023, 4, 5, 11, 23), new Attendee(1, "Nathan Vonder Haar", "1234"), List.of(new Checkout(1, new Attendee(1, "Brendon Faithfull", "1234"), LocalDateTime.of(2023, 4, 4, 4, 23, 34), LocalDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
                    new GameCopy(154, "154", "Play and Win", "Geekway", "Donated by Miniature Market", CheckoutStatus.CHECKED_OUT, LocalDateTime.of(2023, 4, 5, 11, 23), new Attendee(2, "Aaron Bruce", "3456"), List.of(new Checkout(1, new Attendee(1, "Brendon Faithfull", "1234"), LocalDateTime.of(2023, 4, 4, 4, 23, 34), LocalDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now()))))), CheckoutStatus.AVAILABLE, 1, 1),
            new Game(2222, "Terra Mystica", List.of(
                    new GameCopy(567, "567", "Common", "Zack Brown", "Mint condition", CheckoutStatus.AVAILABLE, LocalDateTime.of(2023, 4, 5, 11, 23), null, List.of(new Checkout(1, new Attendee(1, "Brendon Faithfull", "1234"), LocalDateTime.of(2023, 4, 4, 4, 23, 34), LocalDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
                    new GameCopy(333, "333", "Play and Win", "Geekway", "Donated by TMG", CheckoutStatus.EXTREME_OVERDUE, LocalDateTime.of(2023, 4, 5, 11, 23), new Attendee(1, "Nathan Vonder Haar", "1234"), List.of(new Checkout(1, new Attendee(1, "Brendon Faithfull", "1234"), LocalDateTime.of(2023, 4, 4, 4, 23, 34), LocalDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
                    new GameCopy(444, "444", "Play and Win", "Geekway", "Donated by Miniature Market", CheckoutStatus.CHECKED_OUT, LocalDateTime.of(2023, 4, 5, 11, 23), new Attendee(2, "Aaron Bruce", "3456"), List.of(new Checkout(1, new Attendee(1, "Brendon Faithfull", "1234"), LocalDateTime.of(2023, 4, 4, 4, 23, 34), LocalDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now()))))), CheckoutStatus.AVAILABLE, 3, 2),
            new Game(145, "Ticket to Ride", List.of(
                    new GameCopy(999, "999", "Common", "Zack Brown", "Mint condition", CheckoutStatus.AVAILABLE, LocalDateTime.of(2023, 4, 5, 11, 23), null, List.of(new Checkout(1, new Attendee(1, "Brendon Faithfull", "1234"), LocalDateTime.of(2023, 4, 4, 4, 23, 34), LocalDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
                    new GameCopy(908, "908", "Play and Win", "Geekway", "Donated by TMG", CheckoutStatus.EXTREME_OVERDUE, LocalDateTime.of(2023, 4, 5, 11, 23), new Attendee(1, "Nathan Vonder Haar", "1234"), List.of(new Checkout(1, new Attendee(1, "Brendon Faithfull", "1234"), LocalDateTime.of(2023, 4, 4, 4, 23, 34), LocalDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
                    new GameCopy(5674, "5674", "Play and Win", "Geekway", "Donated by Miniature Market", CheckoutStatus.CHECKED_OUT, LocalDateTime.of(2023, 4, 5, 11, 23), new Attendee(2, "Aaron Bruce", "3456"), List.of(new Checkout(1, new Attendee(1, "Brendon Faithfull", "1234"), LocalDateTime.of(2023, 4, 4, 4, 23, 34), LocalDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now()))))), CheckoutStatus.CHECKED_OUT, 10, 2),
            new Game(1676, "Tzolkin", List.of(
                    new GameCopy(5670, "5670", "Common", "Zack Brown", "Mint condition", CheckoutStatus.AVAILABLE, LocalDateTime.of(2023, 4, 5, 11, 23), null, List.of(new Checkout(1, new Attendee(1, "Brendon Faithfull", "1234"), LocalDateTime.of(2023, 4, 4, 4, 23, 34), LocalDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
                    new GameCopy(1123, "1123", "Play and Win", "Geekway", "Donated by TMG", CheckoutStatus.EXTREME_OVERDUE, LocalDateTime.of(2023, 4, 5, 11, 23), new Attendee(1, "Nathan Vonder Haar", "1234"), List.of(new Checkout(1, new Attendee(1, "Brendon Faithfull", "1234"), LocalDateTime.of(2023, 4, 4, 4, 23, 34), LocalDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
                    new GameCopy(4476, "4476", "Play and Win", "Geekway", "Donated by Miniature Market", CheckoutStatus.CHECKED_OUT, LocalDateTime.of(2023, 4, 5, 11, 23), new Attendee(2, "Aaron Bruce", "3456"), List.of(new Checkout(1, new Attendee(1, "Brendon Faithfull", "1234"), LocalDateTime.of(2023, 4, 4, 4, 23, 34), LocalDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now()))))), CheckoutStatus.EXTREME_OVERDUE, 7, 4),
            new Game(19, "Inis", List.of(
                    new GameCopy(45, "45", "Common", "Zack Brown", "Mint condition", CheckoutStatus.AVAILABLE, LocalDateTime.of(2023, 4, 5, 11, 23), null, List.of(new Checkout(1, new Attendee(1, "Brendon Faithfull", "1234"), LocalDateTime.of(2023, 4, 4, 4, 23, 34), LocalDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
                    new GameCopy(67, "67", "Play and Win", "Geekway", "Donated by TMG", CheckoutStatus.EXTREME_OVERDUE, LocalDateTime.of(2023, 4, 5, 11, 23), new Attendee(1, "Nathan Vonder Haar", "1234"), List.of(new Checkout(1, new Attendee(1, "Brendon Faithfull", "1234"), LocalDateTime.of(2023, 4, 4, 4, 23, 34), LocalDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
                    new GameCopy(987, "987", "Play and Win", "Geekway", "Donated by Miniature Market", CheckoutStatus.CHECKED_OUT, LocalDateTime.of(2023, 4, 5, 11, 23), new Attendee(2, "Aaron Bruce", "3456"), List.of(new Checkout(1, new Attendee(1, "Brendon Faithfull", "1234"), LocalDateTime.of(2023, 4, 4, 4, 23, 34), LocalDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now()))))), CheckoutStatus.NEAR_OVERDUE, 5, 5),
            new Game(10, "Scythe", List.of(
                    new GameCopy(3867, "3867", "Common", "Zack Brown", "Mint condition", CheckoutStatus.AVAILABLE, LocalDateTime.of(2023, 4, 5, 11, 23), null, List.of(new Checkout(1, new Attendee(1, "Brendon Faithfull", "1234"), LocalDateTime.of(2023, 4, 4, 4, 23, 34), LocalDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
                    new GameCopy(9045, "9045", "Play and Win", "Geekway", "Donated by TMG", CheckoutStatus.EXTREME_OVERDUE, LocalDateTime.of(2023, 4, 5, 11, 23), new Attendee(1, "Nathan Vonder Haar", "1234"), List.of(new Checkout(1, new Attendee(1, "Brendon Faithfull", "1234"), LocalDateTime.of(2023, 4, 4, 4, 23, 34), LocalDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
                    new GameCopy(225, "225", "Play and Win", "Geekway", "Donated by Miniature Market", CheckoutStatus.CHECKED_OUT, LocalDateTime.of(2023, 4, 5, 11, 23), new Attendee(2, "Aaron Bruce", "3456"), List.of(new Checkout(1, new Attendee(1, "Brendon Faithfull", "1234"), LocalDateTime.of(2023, 4, 4, 4, 23, 34), LocalDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now()))))), CheckoutStatus.OVERDUE, 0, 6));

    @GetMapping("/old/index")
    public String index(Model model) {
        model.addAttribute("games", List.of(
                new OldGame(1, "Carcassone", List.of(new Copy("1"), new Copy("2")), true, CheckoutStatus.AVAILABLE, 0, 0),
                new OldGame(1, "Love Letter", List.of(new Copy("3")), true, CheckoutStatus.AVAILABLE, 0, 0)
        ));
        return "old/games/index";
    }

    @GetMapping("/old/create")
    public String create(Model model) {
        return "old/games/create";
    }

    @GetMapping("/old/details")
    public String details(Model model) {
        model.addAttribute("game", new OldGame(1, "Carcassone", List.of(new Copy("1"), new Copy("2")), true, CheckoutStatus.AVAILABLE, 0, 0));
        return "old/games/details";
    }

    @GetMapping("/old/edit")
    public String edit(Model model) {
        return "old/games/edit";
    }

    @GetMapping("/old/delete")
    public String delete(Model model) {
        model.addAttribute("game", new OldGame(1, "Carcassone", List.of(new Copy("1"), new Copy("2")), true, CheckoutStatus.AVAILABLE, 0, 0));
        return "old/games/delete";
    }

    @GetMapping
    public String games(Model model) {
        model.addAttribute("games", GAMES);
        return "game/games";
    }

    @GetMapping("/{gameId}/copies")
    public String copies(@PathVariable("gameId") long gameId, Model model) {
        Game game = GAMES.stream().filter(g -> g.id() == gameId).findFirst().orElseThrow();
        model.addAttribute("game", game);
        model.addAttribute("copies", game.copies());
        return "game/copies";
    }

    @GetMapping("/{gameId}/copies/clear")
    public String copiesClear(@PathVariable("gameId") long gameId, Model model) {
        Game game = GAMES.stream().filter(g -> g.id() == gameId).findFirst().orElseThrow();
        model.addAttribute("game", game);
        return "game/copies_clear";
    }

    @GetMapping("/copies/{copyId}")
    public String copyDetails(@PathVariable("copyId") long copyId, Model model) {
        GameCopy gameCopy = GAMES.stream().flatMap(g -> g.copies().stream()).filter(copy -> copy.id() == copyId).findFirst().orElseThrow();
        Game game = GAMES.stream().filter(g -> g.copies().contains(gameCopy)).findFirst().orElseThrow();
        model.addAttribute("game", game);
        model.addAttribute("copy", gameCopy);
        return "game/copy_dialog";
    }

    @GetMapping("/copies/{copyId}/edit")
    public String copyEdit(@PathVariable("copyId") long copyId, Model model) {
        GameCopy gameCopy = GAMES.stream().flatMap(g -> g.copies().stream()).filter(copy -> copy.id() == copyId).findFirst().orElseThrow();
        Game game = GAMES.stream().filter(g -> g.copies().contains(gameCopy)).findFirst().orElseThrow();
        model.addAttribute("game", game);
        model.addAttribute("copy", gameCopy);
        return "game/copy_fields_edit";
    }

    @GetMapping("/copies/{copyId}/cancel")
    public String copyCancel(@PathVariable("copyId") long copyId, Model model) {
        GameCopy gameCopy = GAMES.stream().flatMap(g -> g.copies().stream()).filter(copy -> copy.id() == copyId).findFirst().orElseThrow();
        Game game = GAMES.stream().filter(g -> g.copies().contains(gameCopy)).findFirst().orElseThrow();
        model.addAttribute("game", game);
        model.addAttribute("copy", gameCopy);
        return "game/copy_fields";
    }

    @PatchMapping("/copies/{copyId}")
    public String copySave(@PathVariable("copyId") long copyId,
                           @RequestParam("library") String library,
                           @RequestParam("libraryId") String libraryId,
                           @RequestParam("owner") String owner,
                           @RequestParam("notes") String notes,
                           Model model) {
        GameCopy gameCopy = GAMES.stream().flatMap(g -> g.copies().stream()).filter(copy -> copy.id() == copyId).findFirst().orElseThrow();
        Game game = GAMES.stream().filter(g -> g.copies().contains(gameCopy)).findFirst().orElseThrow();
        model.addAttribute("game", game);
        model.addAttribute("copy", gameCopy);
        return "game/copy_fields";
    }

    @DeleteMapping("/copies/{copyId}")
    public String copyDelete(@PathVariable("copyId") long copyId, Model model) {
        GameCopy gameCopy = GAMES.stream().flatMap(g -> g.copies().stream()).filter(copy -> copy.id() == copyId).findFirst().orElseThrow();
        Game game = GAMES.stream().filter(g -> g.copies().contains(gameCopy)).findFirst().orElseThrow();
        model.addAttribute("game", game);
        model.addAttribute("copy", gameCopy);
        return "game/copy_delete_confirm_dialog";
    }
}

