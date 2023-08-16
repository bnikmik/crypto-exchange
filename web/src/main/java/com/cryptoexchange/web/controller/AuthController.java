package com.cryptoexchange.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login/")
public class AuthController {

    @GetMapping
    public String login(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        return "loginInfo";
    }

}
