package com.cryptoexchange.auction.controller;

import com.cryptoexchange.auction.dto.AuctionDTO;
import com.cryptoexchange.auction.service.AuctionService;
import com.cryptoexchange.common.exception.ResponseWrapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Instant;

@RestController
@AllArgsConstructor
@RequestMapping("/auctions")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Аукцион")
public class AuctionController {

    private final AuctionService service;

    @PostMapping
    public ResponseEntity<?> createAuction(@Valid @RequestBody AuctionDTO auctionDTO) {
        ResponseWrapper<AuctionDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.createAuction(auctionDTO), null);
        return ResponseEntity.ok(wrapper);
    }

}
