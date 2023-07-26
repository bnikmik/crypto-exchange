package com.example.controller;

import com.example.dto.AccountDTO;
import com.example.service.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

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
    public ResponseEntity<?> findAccount(@PathVariable Long id) {
        return ResponseEntity.ok(service.findAccount(id));
    }

    @GetMapping()
    public ResponseEntity<?> findAllAccounts() {
        return ResponseEntity.ok(service.findAllAccounts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccountById(@PathVariable Long id, @Valid @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(service.updateAccountById(id, accountDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccountById(@PathVariable Long id) {
        service.deleteAccountById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<?> deposit(@PathVariable Long id, @RequestParam BigDecimal value) {
        service.deposit(id, value);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/withdrawal")
    public ResponseEntity<?> withdrawal(@PathVariable Long id, @RequestParam BigDecimal value) {
        service.deposit(id, value);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<?> getBalance(@PathVariable Long id) {
        return ResponseEntity.ok(service.getBalance(id));
    }
}
