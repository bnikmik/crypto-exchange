package com.cryptoexchange.web.controller;

import com.cryptoexchange.common.keycloak.CustomClaimsService;
import com.cryptoexchange.web.service.CustomerClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
@AllArgsConstructor
public class MainController {
    private final CustomClaimsService claimsService;
    private final CustomerClientService clientService;

    @GetMapping("/main-menu")
    public String login() {
        if (!clientService.checkCustomerRegistration
                (claimsService.getLoggedUserId())) {
            clientService.createCustomer();
        }
        return "main-menu";
    }

    @GetMapping()
    public String getStartPage() {
        return "main";
    }
}
