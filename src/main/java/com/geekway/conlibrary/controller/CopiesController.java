package com.geekway.conlibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Controller
@RequestMapping("/copies")
public class CopiesController {

    private static final DateTimeFormatter CHECK_OUT_IN_FORMAT = DateTimeFormatter.ofLocalizedDateTime(
            FormatStyle.FULL,
            FormatStyle.SHORT
    ).withZone(ZoneOffset.of("-06:00"));

    public record Copy(String libraryId, String ownerName,
                       String notes, Checkout currentCheckout) {
    }

    public record Attendee(String name) {
    }

    public record Checkout(Attendee attendee, String start) {
    }

    public record Game(String title) {
    }

    @GetMapping("/old/index")
    public String index(Model model) {
        model.addAttribute("game", new Game("Power Grid"));
        model.addAttribute("copies", List.of(
                new Copy("123", "Aaron Bruce", "Good condition", new Checkout(new Attendee("Zack Brown"), CHECK_OUT_IN_FORMAT.format(Instant.now()))),
                new Copy("124", "Brendon Faithfull", "Covered in unknown substance, probably biohazard", new Checkout(new Attendee("Zack Brown"), CHECK_OUT_IN_FORMAT.format(Instant.now())))
        ));
        return "old/copies/index";
    }

    @GetMapping("/old/create")
    public String create(Model model) {
        model.addAttribute(new Game("Power Grid"));
        return "old/copies/create";
    }

    @GetMapping("/old/details")
    public String details(Model model) {
        model.addAttribute("game", new Game("Power Grid"));
        model.addAttribute("copy", new Copy("1", "Zack Brown", "Mint condition", new Checkout(new Attendee("Zack Brown"), CHECK_OUT_IN_FORMAT.format(Instant.now()))));
        return "old/copies/details";
    }

    @GetMapping("/old/edit")
    public String edit(Model model) {
        return "old/copies/edit";
    }

    @GetMapping("/old/delete")
    public String delete(Model model) {
        model.addAttribute("copy", new Copy("1", "Zack Brown", "Mint condition", new Checkout(new Attendee("Zack Brown"), CHECK_OUT_IN_FORMAT.format(Instant.now()))));
        return "old/copies/delete";
    }
}

