package com.cryptoexchange.web.controller;

import com.cryptoexchange.common.dto.AuctionDTO;
import com.cryptoexchange.common.model.Currency;
import com.cryptoexchange.web.service.AuctionClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
@RequestMapping("/auctions")
@AllArgsConstructor
public class AuctionController {

    private final AuctionClientService auctionClientService;

    @GetMapping
    public String getAuctionPage(Model model) {
        model.addAttribute("auctionList", auctionClientService.findAllAuctions());
        return "auctions";
    }

    @GetMapping("/{id}")
    public String getAuctionByIdPage(@PathVariable UUID id, Model model) {
        AuctionDTO auctionDTO = auctionClientService.findAuctionById(id);
        model.addAttribute("auctionId", id);
        model.addAttribute("currency", auctionDTO.getCurrency());
        model.addAttribute("amount", auctionDTO.getAmountCoins());
        model.addAttribute("pricePerUnit", auctionDTO.getCoinPrice());
        return "auction-by-id";
    }

    @PostMapping("/start")
    public String startAuctionDeal(@RequestParam(name = "auctionId") UUID id,
                                   @RequestParam(name = "amountCoins") BigDecimal amountCoins) {
        UUID uuid = auctionClientService.startAuctionDeal(id, amountCoins);
        return "redirect:/deals/" + uuid.toString();
    }

    @GetMapping("/create")
    public String getCreateAuctionPage() {
        return "auctions-create";
    }

    @PostMapping("/create")
    public String createNewAuction(@RequestParam("accountType") Currency currency,
                                   @RequestParam("amount") BigDecimal amount,
                                   @RequestParam("pricePerUnit") BigDecimal pricePerUnit) {
        auctionClientService.createAuction(currency, amount, pricePerUnit);
        return "redirect:/auctions";
    }
}
