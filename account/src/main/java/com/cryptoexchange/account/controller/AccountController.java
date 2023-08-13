package com.cryptoexchange.account.controller;

import com.cryptoexchange.account.dto.AccountDTO;
import com.cryptoexchange.account.dto.BalanceDTO;
import com.cryptoexchange.account.service.AccountService;
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
@RequestMapping("/accounts")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Счета")
public class AccountController {

    private final AccountService service;

    @PostMapping()
    @Operation(summary = "Создать новый счет")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AccountDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Введены неверные параметры.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Не авторизован. Используйте обновленный bearer токен.", content = @Content)})
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        ResponseWrapper<AccountDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.createAccount(accountDTO), null);
        return ResponseEntity.ok(wrapper);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить счет по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AccountDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Не авторизован. Используйте обновленный bearer токен.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Счет не найден.", content = @Content)})
    public ResponseEntity<?> findAccountById(@PathVariable UUID id) {
        ResponseWrapper<AccountDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.findAccountById(id), null);
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping()
    @Operation(summary = "Получить все счета")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = AccountDTO.class)))}),
            @ApiResponse(responseCode = "401", description = "Не авторизован. Используйте обновленный bearer токен.", content = @Content)})
    public ResponseEntity<?> findAllAccounts() {
        ResponseWrapper<List<AccountDTO>> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.findAllAccounts(), null);
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Деактивировать счет по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content),
            @ApiResponse(responseCode = "401", description = "Не авторизован. Используйте обновленный bearer токен.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Счет не найден.", content = @Content)})
    public ResponseEntity<?> deleteAccountById(@PathVariable UUID id) {
        service.deleteAccountById(id);
        ResponseWrapper<String> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, "Счет деактивирован", null);
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/balance/{accountId}")
    @Operation(summary = "Получить баланс счета по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AccountDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Не авторизован. Используйте обновленный bearer токен.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Счет не найден.", content = @Content)})
    public ResponseEntity<?> calcBalance(@PathVariable UUID accountId) {
        ResponseWrapper<BalanceDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.getBalance(accountId), null);
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }
}
