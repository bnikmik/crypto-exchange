package com.cryptoexchange.account.controller;

import com.cryptoexchange.account.dto.AccountDTO;
import com.cryptoexchange.account.dto.BalanceDTO;
import com.cryptoexchange.account.service.AccountService;
import com.cryptoexchange.common.exception.ResponseWrapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
@SecurityRequirement(name = "Bearer Authentication")
public class AccountController {

    private final AccountService service;

    @PostMapping()
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        ResponseWrapper<AccountDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.createAccount(accountDTO));
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findAccount(@PathVariable UUID id) {
        ResponseWrapper<AccountDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.findAccount(id));
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> findAllAccounts() {
        ResponseWrapper<List<AccountDTO>> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.findAllAccounts());
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccountById(@PathVariable UUID id) {
        service.deleteAccountById(id);
        ResponseWrapper<String> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, "Счет деактивирован");
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/balance/{accountId}")
    public ResponseEntity<?> calcBalance(@PathVariable UUID accountId) {
        ResponseWrapper<BalanceDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.getBalance(accountId));
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }
}
