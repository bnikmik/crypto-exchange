package com.cryptoexchange.customer.controller;

import com.cryptoexchange.common.exception.ResponseWrapper;
import com.cryptoexchange.common.model.Currency;
import com.cryptoexchange.customer.dto.CustomerDTO;
import com.cryptoexchange.customer.service.CustomerService;
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
@RequestMapping("/customers")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Пользователи")
public class CustomerController {

    private final CustomerService service;

    @PostMapping()
    @Operation(summary = "Создать нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Введены неверные параметры.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Не авторизован. Используйте обновленный bearer токен.", content = @Content)})
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        ResponseWrapper<CustomerDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.createCustomer(customerDTO), null);
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @PostMapping("/{id}/add-account")
    @Operation(summary = "Добавить пользователю счет по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Введены неверные параметры.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Не авторизован. Используйте обновленный bearer токен.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден.", content = @Content)})
    public ResponseEntity<?> addAccountForCustomerById(@PathVariable UUID id, @RequestBody Currency currency) {
        ResponseWrapper<CustomerDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.addAccountForCustomerById(id, currency), null);
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Не авторизован. Используйте обновленный bearer токен.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден.", content = @Content)})
    public ResponseEntity<?> findCustomerById(@PathVariable UUID id) {
        ResponseWrapper<CustomerDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.findCustomerById(id), null);
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping()
    @Operation(summary = "Получить всех пользоваталей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class)))}),

            @ApiResponse(responseCode = "401", description = "Не авторизован. Используйте обновленный bearer токен.", content = @Content)})
    public ResponseEntity<?> findAllCustomers() {
        ResponseWrapper<List<CustomerDTO>> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.findAllCustomers(), null);
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Введены неверные параметры.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Не авторизован. Используйте обновленный bearer токен.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден.", content = @Content)})
    public ResponseEntity<?> updateCustomerById(@PathVariable UUID id, @Valid @RequestBody CustomerDTO customerDTO) {
        ResponseWrapper<CustomerDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.updateCustomerById(id, customerDTO), null);
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Деактивировать пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content),
            @ApiResponse(responseCode = "401", description = "Не авторизован. Используйте обновленный bearer токен.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден.", content = @Content)})
    public ResponseEntity<?> deleteCustomerById(@PathVariable UUID id) {
        service.deleteCustomerById(id);
        ResponseWrapper<String> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, "Пользователь деактивирован", null);
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }
}
