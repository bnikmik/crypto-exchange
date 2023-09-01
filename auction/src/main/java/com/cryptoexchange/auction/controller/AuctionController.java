package com.cryptoexchange.auction.controller;

import com.cryptoexchange.auction.dto.AuctionDTO;
import com.cryptoexchange.auction.dto.AuctionStartDTO;
import com.cryptoexchange.auction.service.AuctionService;
import com.cryptoexchange.common.exception.ResponseWrapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

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

    @PutMapping("/{id}")
    public ResponseEntity<?> startAuctionDeal(@PathVariable UUID id, @RequestBody AuctionStartDTO amountDTO) {
        ResponseWrapper<AuctionDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.startAuctionDeal(id, amountDTO), null);
        return ResponseEntity.ok(wrapper);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelAuctionDeal(@PathVariable UUID id, @RequestBody AuctionStartDTO amountDTO) {
        ResponseWrapper<AuctionDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.cancelAuctionDeal(id, amountDTO), null);
        return ResponseEntity.ok(wrapper);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findAuctionById(@PathVariable UUID id) {
        ResponseWrapper<AuctionDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.findAuctionById(id), null);
        return ResponseEntity.ok(wrapper);
    }

    @GetMapping()
    public ResponseEntity<?> findAllAuctions() {
        ResponseWrapper<List<AuctionDTO>> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.findAllAuctions(), null);
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }
}
