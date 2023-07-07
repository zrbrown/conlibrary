package com.geekway.conlibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/old/index")
    public String index() {
        return "old/home/index";
    }

    @GetMapping
    public String home(Model model) {
        return "home";
    }
}
