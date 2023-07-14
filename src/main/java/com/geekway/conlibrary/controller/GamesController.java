package com.geekway.conlibrary.controller;

import com.geekway.conlibrary.model.Checkout;
import com.geekway.conlibrary.model.GameCopyDetail;
import com.geekway.conlibrary.model.GameCopySummary;
import com.geekway.conlibrary.model.GameSummary;
import com.geekway.conlibrary.service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/games")
public class GamesController {

    private final GameService gameService;

    public GamesController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public String games(Model model) {
        model.addAttribute("games", gameService.getGames());
        return "game/games";
    }

    @GetMapping("/{gameId}/copies")
    public String copies(@PathVariable("gameId") long gameId, Model model) {
        // TODO virutal thread these
        GameSummary gameSummary = gameService.getGame(gameId);
        List<GameCopySummary> gameCopySummaries = gameService.getGameCopies(gameId);

        model.addAttribute("game", gameSummary);
        model.addAttribute("copies", gameCopySummaries);
        return "game/copies";
    }

    @GetMapping("/{gameId}/copies/clear")
    public String copiesClear(@PathVariable("gameId") long gameId, Model model) {
        model.addAttribute("game", gameService.getGame(gameId));
        return "game/copies_clear";
    }

    @GetMapping("/copies/{gameCopyId}")
    public String copyDetails(@PathVariable("gameCopyId") long gameCopyId, Model model) {
        // TODO virutal thread these
        GameCopyDetail gameCopyDetail = gameService.getGameCopy(gameCopyId);
        List<Checkout> checkouts = gameService.getCheckouts(gameCopyId);

        model.addAttribute("copy", gameCopyDetail);
        model.addAttribute("checkouts", checkouts);
        return "game/copy_dialog";
    }

    @GetMapping("/copies/{gameCopyId}/edit")
    public String copyEdit(@PathVariable("gameCopyId") long gameCopyId, Model model) {
        model.addAttribute("copy", gameService.getGameCopy(gameCopyId));
        return "game/copy_fields_edit";
    }

    @GetMapping("/copies/{gameCopyId}/cancel")
    public String copyCancel(@PathVariable("gameCopyId") long gameCopyId, Model model) {
        model.addAttribute("copy", gameService.getGameCopy(gameCopyId));
        return "game/copy_fields";
    }

    @PatchMapping("/copies/{gameCopyId}")
    public String copySave(@PathVariable("gameCopyId") long gameCopyId,
                           @RequestParam("libraryGameCopyId") long libraryGameCopyId,
                           @RequestParam("libraryCopyId") String libraryCopyId,
                           @RequestParam("libraryId") long libraryId,
                           @RequestParam("owner") String owner,
                           @RequestParam("notes") String notes,
                           Model model) {
        model.addAttribute("copy",
                gameService.updateGameCopy(gameCopyId, libraryGameCopyId, libraryCopyId, libraryId, owner, notes));
        return "game/copy_fields";
    }

    @DeleteMapping("/copies/{gameCopyId}")
    public String copyDelete(@PathVariable("gameCopyId") long gameCopyId, Model model) {
        model.addAttribute("copy", gameService.getGameCopy(gameCopyId));
        return "game/copy_delete_confirm_dialog";
    }
}

