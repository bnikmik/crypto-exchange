package com.cryptoexchange.web.controller;

import com.cryptoexchange.common.keycloak.CustomClaimsService;
import com.cryptoexchange.web.service.CustomerClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
@AllArgsConstructor
public class AuthController {
    private final CustomClaimsService claimsService;
    private final CustomerClientService clientService;

    @GetMapping("/login")
    public String login(Model model) {
        if (!clientService.checkCustomerRegistration
                (claimsService.getLoggedUserId())) {
            clientService.createCustomer();
        }
        model.addAttribute("username", claimsService.getLoggedUserId());
        return "loginInfo";
    }

    @GetMapping()
    public String getStartPage(Model model) {
        return "main";
    }
}
