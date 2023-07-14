package com.geekway.conlibrary.controller;

import com.geekway.conlibrary.db.dto.AttendeeCheckoutDetailDto;
import com.geekway.conlibrary.db.dto.GameCopyCheckoutDetailDto;
import com.geekway.conlibrary.service.CheckoutService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/checkouts")
public class CheckoutsController {

    private final CheckoutService checkoutService;

    public CheckoutsController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    public record Incidental(String gameTitles, String attendeeName, String attendeeSurname) {
    }

    public record Checkin(long checkoutId, String gameTitle, String attendeeName, String attendeeSurname) {
    }

    public record GameCopy(long id, String title) {
    }

    public record Attendee(long id, String name, String currentCheckouts, long currentCheckoutCount) {
    }

    public record CheckoutModel(GameCopy gameCopy, Attendee attendee) {
    }

    @GetMapping
    public String checkOutIn(Model model) {
        return "check_in_out/check_out_in";
    }

    @PostMapping("/barcode/game")
    public String barcodeGame(@RequestParam("gameScanInput") String gameScanInput, Model model) {
        //TODO virtual thread these
        Optional<GameCopyCheckoutDetailDto> libraryGameCopy = checkoutService.searchGameBarcode(gameScanInput);
        Optional<AttendeeCheckoutDetailDto> incidentalAttendee = checkoutService.searchAttendeeBarcode(gameScanInput);

        incidentalAttendee.ifPresent(a -> {
            //TODO if checked out copy and attendee are the same as libraryGameCopy's, then don't do this - the attendee has checked out a game with the same id as their badge
            model.addAttribute("incidental", new CheckoutsController.Incidental(a.gameTitles(), a.attendeeName(), a.attendeeSurname()));
        });

        model.addAttribute("gameScanInput", gameScanInput);

        return libraryGameCopy.map(gc -> {
            if (gc.checkoutId() != null) {
                model.addAttribute("checkin", new Checkin(gc.checkoutId(), gc.gameTitle(), gc.attendeeName(), gc.attendeeSurname()));
                return "check_in_out/checkin_dialog";
            } else {
                model.addAttribute("game", new GameCopy(gc.libraryGameCopyId(), gc.gameTitle()));
                return "check_in_out/checkout_dialog";
            }
        }).orElse("check_in_out/check_out_in_game_not_found");
    }

    @PostMapping("/barcode/attendee")
    public String barcodeAttendee(@RequestParam("badgeScanInput") String badgeScanInput,
                                  @RequestParam("libraryGameCopyId") long libraryGameCopyId,
                                  @RequestParam("gameTitle") String gameTitle,
                                  Model model) {
        //TODO virtual thread these
        Optional<AttendeeCheckoutDetailDto> attendee = checkoutService.searchAttendeeBarcode(badgeScanInput);
        Optional<GameCopyCheckoutDetailDto> incidentalLibraryGameCopy = checkoutService.searchGameBarcode(badgeScanInput);

        incidentalLibraryGameCopy.ifPresent(gc -> {
            //TODO if checked out copy and attendee are the same as libraryGameCopy's, then don't do this - the attendee has checked out a game with the same id as their badge
            model.addAttribute("incidental", new Incidental(gc.gameTitle(), gc.attendeeName(), gc.attendeeSurname()));
        });

        model.addAttribute("badgeScanInput", badgeScanInput);

        return attendee.map(a -> {
            model.addAttribute("checkout", new CheckoutModel(
                    new GameCopy(libraryGameCopyId, gameTitle),
                    new Attendee(a.attendeeId(), a.attendeeName(), a.gameTitles(), a.checkoutCount())
            ));
            return "check_in_out/check_out_confirm";
        }).orElse("check_in_out/check_out_attendee_not_found");
    }

    //TODO calling checkout on a checked out copy?
    @PostMapping("/checkout")
    public String checkout(@RequestParam("libraryCopyId") long libraryCopyId,
                           @RequestParam("attendeeId") long attendeeId,
                           @RequestParam(value = "overrideLimit", defaultValue = "false") boolean overrideLimit,
                           Model model) {
        checkoutService.checkout(libraryCopyId, attendeeId, overrideLimit);

        return "check_in_out/check_out_successful";
    }

    //TODO calling checkin on a checked in copy?
    @PostMapping("/checkin")
    public String checkin(@RequestParam("checkoutId") long checkoutId,
                          Model model) {
        checkoutService.checkin(checkoutId);

        return "check_in_out/check_in_successful";
    }
}
