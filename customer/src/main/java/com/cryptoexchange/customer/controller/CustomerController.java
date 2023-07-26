package com.cryptoexchange.customer.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        return ResponseEntity.ok(service.createCustomer(customerDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Не авторизован. Используйте обновленный bearer токен.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден.", content = @Content)})
    public ResponseEntity<?> findCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findCustomerById(id));
    }

    @GetMapping()
    @Operation(summary = "Получить всех пользоваталей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class)))}),

            @ApiResponse(responseCode = "401", description = "Не авторизован. Используйте обновленный bearer токен.", content = @Content)})
    public ResponseEntity<?> findAllCustomers() {
        return ResponseEntity.ok(service.findAllCustomers());
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
        return ResponseEntity.ok(service.updateCustomerById(id, customerDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Не авторизован. Используйте обновленный bearer токен.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден.", content = @Content)})
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long id) {
        service.deleteCustomerById(id);
        return ResponseEntity.ok().build();
    }
}
