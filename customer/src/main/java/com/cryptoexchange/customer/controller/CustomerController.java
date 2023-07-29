package com.cryptoexchange.customer.controller;

import com.cryptoexchange.common.exception.ResponseWrapper;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
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
        ResponseWrapper<CustomerDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.createCustomer(customerDTO));
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Не авторизован. Используйте обновленный bearer токен.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден.", content = @Content)})
    public ResponseEntity<?> findCustomerById(@PathVariable Long id) {
        ResponseWrapper<CustomerDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.findCustomerById(id));
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping()
    @Operation(summary = "Получить всех пользоваталей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class)))}),

            @ApiResponse(responseCode = "401", description = "Не авторизован. Используйте обновленный bearer токен.", content = @Content)})
    public ResponseEntity<?> findAllCustomers() {
        ResponseWrapper<List<CustomerDTO>> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.findAllCustomers());
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
    public ResponseEntity<?> updateCustomerById(@PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO) {
        ResponseWrapper<CustomerDTO> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, service.updateCustomerById(id, customerDTO));
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content),
            @ApiResponse(responseCode = "401", description = "Не авторизован. Используйте обновленный bearer токен.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден.", content = @Content)})
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long id) {
        service.deleteCustomerById(id);
        ResponseWrapper<String> wrapper = new ResponseWrapper<>(Instant.now(), HttpStatus.OK, "Пользователь деактивирован");
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }
}
