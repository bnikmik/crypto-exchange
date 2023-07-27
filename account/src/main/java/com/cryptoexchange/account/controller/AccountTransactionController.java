package com.cryptoexchange.account.controller;

import com.cryptoexchange.account.service.AccountTransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts/transactions")
@SecurityRequirement(name = "Bearer Authentication")
public class AccountTransactionController {

    private final AccountTransactionService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> findAccount(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findAccountTransactionById(id));
    }

    @GetMapping()
    public ResponseEntity<?> findAllAccounts() {
        return ResponseEntity.ok(service.findAllAccountTransactions());
    }

    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<?> deposit(@PathVariable UUID accountId, @RequestParam BigDecimal value) {
        return ResponseEntity.ok(service.deposit(accountId, value));
    }

    @PostMapping("/withdrawal/{accountId}")
    public ResponseEntity<?> withdrawal(@PathVariable UUID accountId, @RequestParam BigDecimal value) {
        return ResponseEntity.ok(service.withdrawal(accountId, value));
    }

    @GetMapping("/balance/{accountId}")
    public ResponseEntity<?> getBalance(@PathVariable UUID accountId) {
        return ResponseEntity.ok(service.getBalance(accountId));
    }
}
