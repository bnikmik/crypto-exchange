package com.cryptoexchange.deal.controller;

import com.cryptoexchange.common.exception.ResponseWrapper;
import com.cryptoexchange.common.model.DealStatus;
import com.cryptoexchange.deal.dto.DealDTO;
import com.cryptoexchange.deal.service.DealService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/deals")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Сделки")
public class DealController {

    private final DealService service;

    @PostMapping
    public ResponseEntity<?> createNewDeal(@Valid @RequestBody DealDTO dealDTO) {
        ResponseWrapper<DealDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.createNewDeal(dealDTO), null);
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @PutMapping("/{dealId}")
    public ResponseEntity<?> updateCustomerById(@PathVariable UUID dealId, @Valid @RequestParam DealStatus dealStatus) {
        ResponseWrapper<DealDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.updateDealStatusById(dealId, dealStatus), null);
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }
}
