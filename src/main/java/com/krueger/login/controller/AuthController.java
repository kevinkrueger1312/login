package com.krueger.login.controller;

import com.krueger.login.model.UserEntity;
import com.krueger.login.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private SecurityService securityService;


    @GetMapping("/auth/login")
    public String login() {
        return "login_page";
    }


    @GetMapping("/auth/registration")
    public String register(Model model) {
        UserEntity user = new UserEntity();
        model.addAttribute("user", user);
        return "register_page";
    }


    @PostMapping("/perform_registration")
    public String perform_register(@ModelAttribute("user") UserEntity userForm) {
        if (securityService.isUsernameTaken(userForm)) {
            LOG.info("Username '" +userForm.getUsername() + "' already exists!");
        } else {
            securityService.saveNewUser(userForm);
        }

        return "login_page";
    }


    @PostMapping("/perform/login")
    public String perform_login(@ModelAttribute("user") UserEntity userLoginForm) { // user authentication is in progress
        securityService.remindUser(userLoginForm);

        return "greeting";
    }
}
