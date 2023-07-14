package com.geekway.conlibrary.controller;

import com.geekway.conlibrary.db.dto.AttendeeDetailDto;
import com.geekway.conlibrary.model.Activity;
import com.geekway.conlibrary.service.AttendeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/attendees")
public class AttendeesController {

    private final AttendeeService attendeeService;

    public AttendeesController(AttendeeService attendeeService) {
        this.attendeeService = attendeeService;
    }

    public record Attendee(long id, String name, String surname, String pronouns, String badgeId,
                           List<Activity> activities) {
    }

    @GetMapping
    public String attendees(Model model) {
        model.addAttribute("attendees", attendeeService.getAttendees());
        return "attendee/attendees";
    }

    @GetMapping("/{attendeeId}")
    public String attendee(@PathVariable("attendeeId") long attendeeId, Model model) {
        //TODO virtual thread these
        AttendeeDetailDto attendeeDetail = attendeeService.getAttendee(attendeeId);
        List<Activity> activities = attendeeService.getCheckoutActivity(attendeeId);

        model.addAttribute("attendee", attendeeDetail);
        model.addAttribute("activities", activities);

        return "attendee/attendee_dialog";
    }

    @GetMapping("/{attendeeId}/edit")
    public String attendeeEdit(@PathVariable("attendeeId") int attendeeId, Model model) {
        model.addAttribute("attendee", attendeeService.getAttendee(attendeeId));
        return "attendee/attendee_fields_edit";
    }

    @GetMapping("/{attendeeId}/cancel")
    public String attendeeCancel(@PathVariable("attendeeId") int attendeeId, Model model) {
        model.addAttribute("attendee", attendeeService.getAttendee(attendeeId));
        return "attendee/attendee_fields";
    }

    @PatchMapping("/{attendeeId}")
    public String attendeeSave(@PathVariable("attendeeId") long attendeeId,
                               @RequestParam("name") String name,
                               @RequestParam("surname") String surname,
                               @RequestParam("pronouns") String pronouns,
                               @RequestParam("badgeId") String badgeId,
                               Model model) {
        model.addAttribute("attendee",
                attendeeService.updateAttendee(attendeeId, name, surname, pronouns, badgeId));
        return "attendee/attendee_fields";
    }

    @DeleteMapping("/{attendeeId}")
    public String attendeeDelete(@PathVariable("attendeeId") int borrowerId, Model model) {
        model.addAttribute("attendee", attendeeService.getAttendee(borrowerId));
        return "attendee/attendee_delete_confirm_dialog";
    }
}

