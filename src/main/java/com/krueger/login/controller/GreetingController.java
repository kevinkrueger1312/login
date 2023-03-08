package com.krueger.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingController {
    @GetMapping("/me/greeting")
    public String home(Model model) {
        return "greeting";
    }
}
