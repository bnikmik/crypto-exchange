package com.cryptoexchange.account.controller;

import com.cryptoexchange.account.dto.AccountDTO;
import com.cryptoexchange.account.service.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
@SecurityRequirement(name = "Bearer Authentication")
public class AccountController {

    private final AccountService service;

    @PostMapping()
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(service.createAccount(accountDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findAccount(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findAccount(id));
    }

    @GetMapping()
    public ResponseEntity<?> findAllAccounts() {
        return ResponseEntity.ok(service.findAllAccounts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccountById(@PathVariable UUID id) {
        service.deleteAccountById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/balance/{accountId}")
    public ResponseEntity<?> calcBalance(@PathVariable UUID accountId) {
        return ResponseEntity.ok(service.getBalance(accountId));
    }
}
