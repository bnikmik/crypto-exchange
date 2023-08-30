package com.cryptoexchange.web.controller;


import com.cryptoexchange.common.dto.DealDTO;
import com.cryptoexchange.common.keycloak.CustomClaimsService;
import com.cryptoexchange.common.model.DealStatus;
import com.cryptoexchange.web.service.DealClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/deals")
@AllArgsConstructor
public class DealController {
    private final DealClientService dealClientService;
    private final CustomClaimsService claimsService;

    @GetMapping("/{id}")
    public String getDealPage(@PathVariable UUID id, Model model) {
        DealDTO dealById = dealClientService.findDealById(id);
        model.addAttribute("dealId", id);
        String loggedUserId = claimsService.getLoggedUserId();
        model.addAttribute("userId", loggedUserId);
        model.addAttribute("buyerId", dealById.getBuyerId().toString());
        model.addAttribute("sellerId", dealById.getSellerId().toString());
        return "deals";
    }

    @PostMapping("/{id}")
    public String updateDealById(@PathVariable UUID id, DealStatus dealStatus) {
        dealClientService.updateDealById(id, dealStatus);
        return "redirect:/accounts";
    }
}
