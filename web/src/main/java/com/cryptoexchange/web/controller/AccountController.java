package com.cryptoexchange.web.controller;

import com.cryptoexchange.common.dto.AccountDTO;
import com.cryptoexchange.common.keycloak.CustomClaimsService;
import com.cryptoexchange.common.keycloak.KeycloakTokenService;
import com.cryptoexchange.common.model.Currency;
import com.cryptoexchange.web.service.AccountClientService;
import com.cryptoexchange.web.service.CustomerClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountClientService accountClientService;
    private final CustomerClientService customerClientService;
    private final CustomClaimsService claimsService;
    private final KeycloakTokenService keycloakTokenService;


    @GetMapping
    public String getCustomerAccounts(Model model) {

        String accessToken = "Bearer " + keycloakTokenService.getToken();
        model.addAttribute("accessToken", accessToken);

        List<AccountDTO> allAccountsByCustomerId =
                accountClientService.findAllAccountsByCustomerId(claimsService.getLoggedUserId());

        List<String> accountList = new ArrayList<>();

        for (AccountDTO accountDTO : allAccountsByCustomerId) {
            accountList.add("Счет: " + accountDTO.getId() +
                    " Тип: " + accountDTO.getCurrency() +
                    " Баланс: " + accountDTO.getBalance());
        }

        model.addAttribute("accountList", accountList);
        return "accounts";
    }

    @PostMapping("/add-account")
    public String addAccount(@RequestParam("accountType") Currency currency, Model model) {
        model.addAttribute("accessToken", "Bearer " + keycloakTokenService.getToken());
        customerClientService.addAccount(currency);
        System.out.println();
        return "redirect:/accounts";
    }

}
