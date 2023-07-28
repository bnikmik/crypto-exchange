package com.cryptoexchange.account.controller;

import com.cryptoexchange.account.dto.AccountTransactionDTO;
import com.cryptoexchange.account.service.AccountTransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> deposit(@PathVariable UUID accountId, @RequestBody AccountTransactionDTO accountTransactionDTO) {
        return ResponseEntity.ok(service.deposit(accountId, accountTransactionDTO));
    }

    @PostMapping("/withdrawal/{accountId}")
    public ResponseEntity<?> withdrawal(@PathVariable UUID accountId, @RequestBody AccountTransactionDTO accountTransactionDTO) {
        return ResponseEntity.ok(service.withdrawal(accountId, accountTransactionDTO));
    }

    @GetMapping("/balance/{accountId}")
    public ResponseEntity<?> calcBalance(@PathVariable UUID accountId) {
        return ResponseEntity.ok(service.calcBalance(accountId));
    }
}
