package com.cryptoexchange.account.controller;

import com.cryptoexchange.account.dto.AccountTransactionDTO;
import com.cryptoexchange.account.dto.TransactionIdDTO;
import com.cryptoexchange.account.service.AccountTransactionService;
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
@RequestMapping("/accounts/transactions")
@SecurityRequirement(name = "Bearer Authentication")
public class AccountTransactionController {

    private final AccountTransactionService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> findAccountTransaction(@PathVariable UUID id) {
        ResponseWrapper<AccountTransactionDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.findAccountTransactionById(id));
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> findAllAccountTransactions() {
        ResponseWrapper<List<AccountTransactionDTO>> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.findAllAccountTransactions());
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<?> deposit(@PathVariable UUID accountId, @Valid @RequestBody AccountTransactionDTO accountTransactionDTO) {
        ResponseWrapper<TransactionIdDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.deposit(accountId, accountTransactionDTO));
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @PostMapping("/withdrawal/{accountId}")
    public ResponseEntity<?> withdrawal(@PathVariable UUID accountId, @Valid @RequestBody AccountTransactionDTO accountTransactionDTO) {
        ResponseWrapper<TransactionIdDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.withdrawal(accountId, accountTransactionDTO));
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }
}
