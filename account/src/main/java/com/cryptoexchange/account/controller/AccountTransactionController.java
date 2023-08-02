package com.cryptoexchange.account.controller;

import com.cryptoexchange.account.dto.AccountDTO;
import com.cryptoexchange.account.dto.AccountTransactionDTO;
import com.cryptoexchange.account.dto.TransactionIdDTO;
import com.cryptoexchange.account.service.AccountTransactionService;
import com.cryptoexchange.common.exception.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/accounts/transactions")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Транзакции счетов")
public class AccountTransactionController {

    private final AccountTransactionService service;

    @GetMapping("/{id}")
    @Operation(summary = "Получить тразакцию по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AccountDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Не авторизован. Используйте обновленный bearer токен.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Транзакция не найдена.", content = @Content)})
    public ResponseEntity<?> findAccountTransaction(@PathVariable UUID id) {
        ResponseWrapper<AccountTransactionDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.findAccountTransactionById(id));
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping()
    @Operation(summary = "Получить все транзакции")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = AccountDTO.class)))}),
            @ApiResponse(responseCode = "401", description = "Не авторизован. Используйте обновленный bearer токен.", content = @Content)})
    public ResponseEntity<?> findAllAccountTransactions() {
        ResponseWrapper<List<AccountTransactionDTO>> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.findAllAccountTransactions());
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @PostMapping("/{accountId}")
    @Operation(summary = "Изъять со счета по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AccountDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Введены неверные параметры.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Не авторизован. Используйте обновленный bearer токен.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Счет не найден.", content = @Content)})
    public ResponseEntity<?> makeTransaction(@PathVariable UUID accountId, @Valid @RequestBody AccountTransactionDTO accountTransactionDTO) {
        ResponseWrapper<TransactionIdDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.makeTransaction(accountId, accountTransactionDTO));
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }
}
