package com.geekway.conlibrary.controller;

import com.geekway.conlibrary.db.dto.GameCopyCheckoutDto;
import com.geekway.conlibrary.db.dto.GameCopyDetailDto;
import com.geekway.conlibrary.db.dto.GameCopyDto;
import com.geekway.conlibrary.db.dto.GameDto;
import com.geekway.conlibrary.db.repository.CheckoutRepository;
import com.geekway.conlibrary.db.repository.GameCopyRepository;
import com.geekway.conlibrary.db.repository.GameRepository;
import com.geekway.conlibrary.model.CheckoutStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

@Controller
@RequestMapping("/games")
public class GamesController {

    private static final DateTimeFormatter CHECK_OUT_IN_FORMAT = DateTimeFormatter.ofLocalizedTime(
            FormatStyle.SHORT
    ).withZone(ZoneOffset.of("-06:00"));

    private static final int NEAR_OVERDUE_MINUTES = 230;
    private static final int OVERDUE_MINUTES = 240;
    private static final int EXTREME_OVERDUE_MINUTES = 480;

    private final GameRepository gameRepository;
    private final GameCopyRepository gameCopyRepository;
    private final CheckoutRepository checkoutRepository;

    public GamesController(GameRepository gameRepository, GameCopyRepository gameCopyRepository, CheckoutRepository checkoutRepository) {
        this.gameRepository = gameRepository;
        this.gameCopyRepository = gameCopyRepository;
        this.checkoutRepository = checkoutRepository;
    }

    public record Copy(String id) {
    }

    public record OldGame(long id, String title, List<Copy> copies, boolean checkedOut, CheckoutStatus checkoutStatus,
                          int playCount, int checkoutCount) {
    }

    public record Game(long id, String title, List<GameCopy> copies, CheckoutStatus checkoutStatus,
                       long playCount, long checkoutCount) {
    }

    public record GameSummary(long id, String title, long copyCount, long currentCheckoutCount, long playCount,
                              CheckoutStatus checkoutStatus) {
    }

    public record Attendee(long id, String name, String surname) {
    }

    public record Checkout(long id, Attendee borrower, OffsetDateTime start, OffsetDateTime end,
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
                           OffsetDateTime checkoutTime, Attendee borrower, List<Checkout> checkouts) {
        public String checkoutTimeDisplay() {
            return checkoutTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US) + " " + CHECK_OUT_IN_FORMAT.format(checkoutTime);
        }

        public Duration checkoutDuration() {
            return Duration.between(checkoutTime(), OffsetDateTime.now());
        }
    }

    public record GameCopySummary(long id, String libraryId, String library, String owner, String notes,
                                  OffsetDateTime activeCheckoutStart, Attendee borrower,
                                  CheckoutStatus checkoutStatus) {
        public String checkoutTimeDisplay() {
            return activeCheckoutStart().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US) + " " + CHECK_OUT_IN_FORMAT.format(activeCheckoutStart());
        }

        public Duration checkoutDuration() {
            return Duration.between(activeCheckoutStart(), OffsetDateTime.now());
        }
    }

    public record GameCopyDetail(long id, String libraryId, String library, String owner, String notes,
                                 String gameTitle, List<Checkout> checkouts) {
    }

//    public static final List<Game> GAMES = List.of(
//            new Game(111, "Power Grid", List.of(
//                    new GameCopy(1467, "1467", "Common", "Zack Brown", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", CheckoutStatus.AVAILABLE, OffsetDateTime.of(2023, 4, 5, 11, 23), null, List.of(
//                            new Checkout(1, new Attendee(1L, "Brendon Faithfull", "1234"), OffsetDateTime.of(2023, 4, 4, 15, 23, 34), OffsetDateTime.of(2023, 4, 4, 20, 56, 0), Duration.between(Instant.now().minusSeconds(800), Instant.now())),
//                            new Checkout(1, new Attendee(1L, "Zack Brown", "1234"), OffsetDateTime.of(2023, 4, 4, 11, 23, 34), OffsetDateTime.of(2023, 4, 4, 13, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())),
//                            new Checkout(1, new Attendee(1L, "Nathan Vonder Haar", "1234"), OffsetDateTime.of(2023, 4, 4, 9, 23, 34), OffsetDateTime.of(2023, 4, 4, 10, 56, 0), Duration.between(Instant.now().minusSeconds(46000), Instant.now())),
//                            new Checkout(1, new Attendee(1L, "Aaron Bruce", "1234"), OffsetDateTime.of(2023, 4, 3, 17, 23, 34), OffsetDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(16000), Instant.now())),
//                            new Checkout(1, new Attendee(1L, "Zack Brown", "1234"), OffsetDateTime.of(2023, 4, 3, 10, 23, 34), OffsetDateTime.of(2023, 4, 3, 12, 56, 0), Duration.between(Instant.now().minusSeconds(5000), Instant.now())))),
//                    new GameCopy(4567, "4567", "Play and Win", "Geekway", "Donated by TMG", CheckoutStatus.EXTREME_OVERDUE, OffsetDateTime.of(2023, 4, 5, 11, 23), new Attendee(1L, "Nathan Vonder Haar", "1234"), List.of(new Checkout(1, new Attendee(1L, "Brendon Faithfull", "1234"), OffsetDateTime.of(2023, 4, 4, 4, 23, 34), OffsetDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
//                    new GameCopy(154, "154", "Play and Win", "Geekway", "Donated by Miniature Market", CheckoutStatus.CHECKED_OUT, OffsetDateTime.of(2023, 4, 5, 11, 23), new Attendee(2L, "Aaron Bruce", "3456"), List.of(new Checkout(1, new Attendee(1L, "Brendon Faithfull", "1234"), OffsetDateTime.of(2023, 4, 4, 4, 23, 34), OffsetDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now()))))), CheckoutStatus.AVAILABLE, 1, 1),
//            new Game(2222, "Terra Mystica", List.of(
//                    new GameCopy(567, "567", "Common", "Zack Brown", "Mint condition", CheckoutStatus.AVAILABLE, OffsetDateTime.of(2023, 4, 5, 11, 23), null, List.of(new Checkout(1, new Attendee(1L, "Brendon Faithfull", "1234"), OffsetDateTime.of(2023, 4, 4, 4, 23, 34), OffsetDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
//                    new GameCopy(333, "333", "Play and Win", "Geekway", "Donated by TMG", CheckoutStatus.EXTREME_OVERDUE, OffsetDateTime.of(2023, 4, 5, 11, 23), new Attendee(1L, "Nathan Vonder Haar", "1234"), List.of(new Checkout(1, new Attendee(1L, "Brendon Faithfull", "1234"), OffsetDateTime.of(2023, 4, 4, 4, 23, 34), OffsetDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
//                    new GameCopy(444, "444", "Play and Win", "Geekway", "Donated by Miniature Market", CheckoutStatus.CHECKED_OUT, OffsetDateTime.of(2023, 4, 5, 11, 23), new Attendee(2L, "Aaron Bruce", "3456"), List.of(new Checkout(1, new Attendee(1L, "Brendon Faithfull", "1234"), OffsetDateTime.of(2023, 4, 4, 4, 23, 34), OffsetDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now()))))), CheckoutStatus.AVAILABLE, 3, 2),
//            new Game(145, "Ticket to Ride", List.of(
//                    new GameCopy(999, "999", "Common", "Zack Brown", "Mint condition", CheckoutStatus.AVAILABLE, OffsetDateTime.of(2023, 4, 5, 11, 23), null, List.of(new Checkout(1, new Attendee(1L, "Brendon Faithfull", "1234"), OffsetDateTime.of(2023, 4, 4, 4, 23, 34), OffsetDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
//                    new GameCopy(908, "908", "Play and Win", "Geekway", "Donated by TMG", CheckoutStatus.EXTREME_OVERDUE, OffsetDateTime.of(2023, 4, 5, 11, 23), new Attendee(1L, "Nathan Vonder Haar", "1234"), List.of(new Checkout(1, new Attendee(1L, "Brendon Faithfull", "1234"), OffsetDateTime.of(2023, 4, 4, 4, 23, 34), OffsetDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
//                    new GameCopy(5674, "5674", "Play and Win", "Geekway", "Donated by Miniature Market", CheckoutStatus.CHECKED_OUT, OffsetDateTime.of(2023, 4, 5, 11, 23), new Attendee(2L, "Aaron Bruce", "3456"), List.of(new Checkout(1, new Attendee(1L, "Brendon Faithfull", "1234"), OffsetDateTime.of(2023, 4, 4, 4, 23, 34), OffsetDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now()))))), CheckoutStatus.CHECKED_OUT, 10, 2),
//            new Game(1676, "Tzolkin", List.of(
//                    new GameCopy(5670, "5670", "Common", "Zack Brown", "Mint condition", CheckoutStatus.AVAILABLE, OffsetDateTime.of(2023, 4, 5, 11, 23), null, List.of(new Checkout(1, new Attendee(1L, "Brendon Faithfull", "1234"), OffsetDateTime.of(2023, 4, 4, 4, 23, 34), OffsetDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
//                    new GameCopy(1123, "1123", "Play and Win", "Geekway", "Donated by TMG", CheckoutStatus.EXTREME_OVERDUE, OffsetDateTime.of(2023, 4, 5, 11, 23), new Attendee(1L, "Nathan Vonder Haar", "1234"), List.of(new Checkout(1, new Attendee(1L, "Brendon Faithfull", "1234"), OffsetDateTime.of(2023, 4, 4, 4, 23, 34), OffsetDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
//                    new GameCopy(4476, "4476", "Play and Win", "Geekway", "Donated by Miniature Market", CheckoutStatus.CHECKED_OUT, OffsetDateTime.of(2023, 4, 5, 11, 23), new Attendee(2L, "Aaron Bruce", "3456"), List.of(new Checkout(1, new Attendee(1L, "Brendon Faithfull", "1234"), OffsetDateTime.of(2023, 4, 4, 4, 23, 34), OffsetDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now()))))), CheckoutStatus.EXTREME_OVERDUE, 7, 4),
//            new Game(19, "Inis", List.of(
//                    new GameCopy(45, "45", "Common", "Zack Brown", "Mint condition", CheckoutStatus.AVAILABLE, OffsetDateTime.of(2023, 4, 5, 11, 23), null, List.of(new Checkout(1, new Attendee(1L, "Brendon Faithfull", "1234"), OffsetDateTime.of(2023, 4, 4, 4, 23, 34), OffsetDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
//                    new GameCopy(67, "67", "Play and Win", "Geekway", "Donated by TMG", CheckoutStatus.EXTREME_OVERDUE, OffsetDateTime.of(2023, 4, 5, 11, 23), new Attendee(1L, "Nathan Vonder Haar", "1234"), List.of(new Checkout(1, new Attendee(1L, "Brendon Faithfull", "1234"), OffsetDateTime.of(2023, 4, 4, 4, 23, 34), OffsetDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
//                    new GameCopy(987, "987", "Play and Win", "Geekway", "Donated by Miniature Market", CheckoutStatus.CHECKED_OUT, OffsetDateTime.of(2023, 4, 5, 11, 23), new Attendee(2L, "Aaron Bruce", "3456"), List.of(new Checkout(1, new Attendee(1L, "Brendon Faithfull", "1234"), OffsetDateTime.of(2023, 4, 4, 4, 23, 34), OffsetDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now()))))), CheckoutStatus.NEAR_OVERDUE, 5, 5),
//            new Game(10, "Scythe", List.of(
//                    new GameCopy(3867, "3867", "Common", "Zack Brown", "Mint condition", CheckoutStatus.AVAILABLE, OffsetDateTime.of(2023, 4, 5, 11, 23), null, List.of(new Checkout(1, new Attendee(1L, "Brendon Faithfull", "1234"), OffsetDateTime.of(2023, 4, 4, 4, 23, 34), OffsetDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
//                    new GameCopy(9045, "9045", "Play and Win", "Geekway", "Donated by TMG", CheckoutStatus.EXTREME_OVERDUE, OffsetDateTime.of(2023, 4, 5, 11, 23), new Attendee(1L, "Nathan Vonder Haar", "1234"), List.of(new Checkout(1, new Attendee(1L, "Brendon Faithfull", "1234"), OffsetDateTime.of(2023, 4, 4, 4, 23, 34), OffsetDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now())))),
//                    new GameCopy(225, "225", "Play and Win", "Geekway", "Donated by Miniature Market", CheckoutStatus.CHECKED_OUT, OffsetDateTime.of(2023, 4, 5, 11, 23), new Attendee(2L, "Aaron Bruce", "3456"), List.of(new Checkout(1, new Attendee(1L, "Brendon Faithfull", "1234"), OffsetDateTime.of(2023, 4, 4, 4, 23, 34), OffsetDateTime.of(2023, 4, 4, 6, 56, 0), Duration.between(Instant.now().minusSeconds(10000), Instant.now()))))), CheckoutStatus.OVERDUE, 0, 6));

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
        OffsetDateTime now = OffsetDateTime.now();
        Page<GameDto> games = gameRepository.getAll(PageRequest.of(0, 100));
        List<GameSummary> gameSummaries = games.stream()
                .map(game -> new GameSummary(
                        game.gameId(), game.gameTitle(), game.copyCount(), game.playCount(), game.currentCheckoutCount(),
                        games.stream()
                                .filter(g -> g.gameId() == game.gameId())
                                .min(Comparator.comparing(GameDto::activeCheckoutStart))//TODO null?
                                .flatMap(g -> g.activeCheckoutStart() == null ? Optional.empty() : Optional.of(Duration.between(g.activeCheckoutStart(), now).toMinutes()))
                                .map(minutesCheckedOut -> {
                                    if (minutesCheckedOut >= EXTREME_OVERDUE_MINUTES) {
                                        return CheckoutStatus.EXTREME_OVERDUE;
                                    } else if (minutesCheckedOut >= OVERDUE_MINUTES) {
                                        return CheckoutStatus.OVERDUE;
                                    } else if (minutesCheckedOut >= NEAR_OVERDUE_MINUTES) {
                                        return CheckoutStatus.NEAR_OVERDUE;
                                    } else {
                                        return CheckoutStatus.CHECKED_OUT;
                                    }
                                })
                                .orElse(CheckoutStatus.AVAILABLE)
                ))
                .toList();
        model.addAttribute("games", gameSummaries);
        return "game/games";
    }

    @GetMapping("/{gameId}/copies")
    public String copies(@PathVariable("gameId") long gameId, Model model) {
        OffsetDateTime now = OffsetDateTime.now();
        GameDto game = gameRepository.getGame(gameId).orElseThrow();
        Long checkoutLength = game.activeCheckoutStart() == null ? null : Duration.between(game.activeCheckoutStart(), now).toMinutes();
        CheckoutStatus status;
        if (checkoutLength == null) {
            status = CheckoutStatus.AVAILABLE;
        } else if (checkoutLength >= EXTREME_OVERDUE_MINUTES) {
            status = CheckoutStatus.EXTREME_OVERDUE;
        } else if (checkoutLength >= OVERDUE_MINUTES) {
            status = CheckoutStatus.OVERDUE;
        } else if (checkoutLength >= NEAR_OVERDUE_MINUTES) {
            status = CheckoutStatus.NEAR_OVERDUE;
        } else {
            status = CheckoutStatus.CHECKED_OUT;
        }
        GameSummary gameSummary = new GameSummary(
                game.gameId(), game.gameTitle(), game.copyCount(), game.playCount(),
                game.currentCheckoutCount(), status
        );
        Page<GameCopyDto> gameCopies = gameCopyRepository.findCopiesByGame(gameId, 1, PageRequest.of(0, 100));
        List<GameCopySummary> gameCopySummaries = gameCopies.stream()
                .map(gc -> new GameCopySummary(
                        gc.copyId(), gc.libraryId(), gc.library(), gc.owner(), gc.notes(),
                        gc.activeCheckoutStart(), gc.attendeeId() == null ? null : new Attendee(
                        gc.attendeeId(), gc.attendeeName(), gc.attendeeSurname()),
                        CheckoutStatus.AVAILABLE
                ))
                .toList();
        model.addAttribute("game", gameSummary);
        model.addAttribute("copies", gameCopySummaries);
        return "game/copies";
    }

    @GetMapping("/{gameId}/copies/clear")
    public String copiesClear(@PathVariable("gameId") long gameId, Model model) {
        OffsetDateTime now = OffsetDateTime.now();
        GameDto game = gameRepository.getGame(gameId).orElseThrow();
        Long checkoutLength = game.activeCheckoutStart() == null ? null : Duration.between(game.activeCheckoutStart(), now).toMinutes();
        CheckoutStatus status;
        if (checkoutLength == null) {
            status = CheckoutStatus.AVAILABLE;
        } else if (checkoutLength >= EXTREME_OVERDUE_MINUTES) {
            status = CheckoutStatus.EXTREME_OVERDUE;
        } else if (checkoutLength >= OVERDUE_MINUTES) {
            status = CheckoutStatus.OVERDUE;
        } else if (checkoutLength >= NEAR_OVERDUE_MINUTES) {
            status = CheckoutStatus.NEAR_OVERDUE;
        } else {
            status = CheckoutStatus.CHECKED_OUT;
        }
        GameSummary gameSummary = new GameSummary(
                game.gameId(), game.gameTitle(), game.copyCount(), game.playCount(),
                game.currentCheckoutCount(), status
        );
        model.addAttribute("game", gameSummary);
        return "game/copies_clear";
    }

    @GetMapping("/copies/{copyId}")
    public String copyDetails(@PathVariable("copyId") long copyId, Model model) {
        OffsetDateTime now = OffsetDateTime.now();
        GameCopyDetailDto gameCopy = gameCopyRepository.findGameCopy(copyId, 1).orElseThrow();
        List<GameCopyCheckoutDto> gameCopyCheckouts = checkoutRepository.getGameCopyCheckouts(copyId);
        List<Checkout> checkouts = new ArrayList<>();
        for (int i = 0; i < gameCopyCheckouts.size(); i++) {
            GameCopyCheckoutDto c = gameCopyCheckouts.get(i);
            OffsetDateTime nextCheckoutStart = i == gameCopyCheckouts.size() - 1 ? now : gameCopyCheckouts.get(i + 1).start();
            OffsetDateTime end = c.end() == null ? now : c.end();

            checkouts.add(new Checkout(
                    c.checkoutId(),
                    new Attendee(
                            c.attendeeId(), c.attendeeName(), c.attendeeSurname()
                    ),
                    c.start(),
                    end,
                    Duration.between(end, nextCheckoutStart)
            ));
        }
        Collections.reverse(checkouts);

        GameCopyDetail gameCopyDetail = new GameCopyDetail(
                gameCopy.copyId(),
                gameCopy.libraryId(),
                gameCopy.library(),
                gameCopy.owner(),
                gameCopy.notes(),
                gameCopy.gameTitle(),
                checkouts
        );
        model.addAttribute("copy", gameCopyDetail);
        return "game/copy_dialog";
    }

    @GetMapping("/copies/{copyId}/edit")
    public String copyEdit(@PathVariable("copyId") long copyId, Model model) {
//        GameCopy gameCopy = GAMES.stream().flatMap(g -> g.copies().stream()).filter(copy -> copy.id() == copyId).findFirst().orElseThrow();
//        Game game = GAMES.stream().filter(g -> g.copies().contains(gameCopy)).findFirst().orElseThrow();
//        model.addAttribute("game", game);
//        model.addAttribute("copy", gameCopy);
        return "game/copy_fields_edit";
    }

    @GetMapping("/copies/{copyId}/cancel")
    public String copyCancel(@PathVariable("copyId") long copyId, Model model) {
//        GameCopy gameCopy = GAMES.stream().flatMap(g -> g.copies().stream()).filter(copy -> copy.id() == copyId).findFirst().orElseThrow();
//        Game game = GAMES.stream().filter(g -> g.copies().contains(gameCopy)).findFirst().orElseThrow();
//        model.addAttribute("game", game);
//        model.addAttribute("copy", gameCopy);
        return "game/copy_fields";
    }

    @PatchMapping("/copies/{copyId}")
    public String copySave(@PathVariable("copyId") long copyId,
                           @RequestParam("library") String library,
                           @RequestParam("libraryId") String libraryId,
                           @RequestParam("owner") String owner,
                           @RequestParam("notes") String notes,
                           Model model) {
//        GameCopy gameCopy = GAMES.stream().flatMap(g -> g.copies().stream()).filter(copy -> copy.id() == copyId).findFirst().orElseThrow();
//        Game game = GAMES.stream().filter(g -> g.copies().contains(gameCopy)).findFirst().orElseThrow();
//        model.addAttribute("game", game);
//        model.addAttribute("copy", gameCopy);
        return "game/copy_fields";
    }

    @DeleteMapping("/copies/{copyId}")
    public String copyDelete(@PathVariable("copyId") long copyId, Model model) {
//        GameCopy gameCopy = GAMES.stream().flatMap(g -> g.copies().stream()).filter(copy -> copy.id() == copyId).findFirst().orElseThrow();
//        Game game = GAMES.stream().filter(g -> g.copies().contains(gameCopy)).findFirst().orElseThrow();
//        model.addAttribute("game", game);
//        model.addAttribute("copy", gameCopy);
        return "game/copy_delete_confirm_dialog";
    }
}

