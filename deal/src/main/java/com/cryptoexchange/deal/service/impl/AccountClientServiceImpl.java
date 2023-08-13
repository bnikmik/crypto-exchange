package com.cryptoexchange.deal.service.impl;

import com.cryptoexchange.common.dto.AccountDTO;
import com.cryptoexchange.common.dto.AccountTransactionDTO;
import com.cryptoexchange.common.dto.TransactionIdDTO;
import com.cryptoexchange.common.exception.ResponseWrapper;
import com.cryptoexchange.common.exception.types.ClientResponseException;
import com.cryptoexchange.common.keycloak.KeycloakTokenService;
import com.cryptoexchange.common.model.TransactionType;
import com.cryptoexchange.deal.service.AccountClientService;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountClientServiceImpl implements AccountClientService {

    private final KeycloakTokenService keycloakTokenService;

    public AccountDTO findAccountById(UUID id) {

        String otherMicroserviceUrl = "http://localhost:8082/accounts/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + keycloakTokenService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        ParameterizedTypeReference<ResponseWrapper<AccountDTO>> responseType =
                new ParameterizedTypeReference<>() {
                };

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<ResponseWrapper<AccountDTO>> responseEntity;
        try {
            responseEntity = new RestTemplate().exchange(
                    otherMicroserviceUrl,
                    HttpMethod.GET,
                    requestEntity,
                    responseType
            );
        } catch (Exception ex) {
            throw new ClientResponseException(ex.getMessage());
        }
        return responseEntity.getBody().getData();
    }

    public void makeTransaction(UUID id, TransactionType type, BigDecimal value) {
        String otherMicroserviceUrl = "http://localhost:8082/accounts/transactions/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + keycloakTokenService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        AccountTransactionDTO transactionDTO = new AccountTransactionDTO();
        transactionDTO.setType(type);
        transactionDTO.setValue(value);

        ParameterizedTypeReference<ResponseWrapper<TransactionIdDTO>> responseType =
                new ParameterizedTypeReference<>() {
                };

        HttpEntity<AccountTransactionDTO> requestEntity = new HttpEntity<>(transactionDTO, headers);

        try {
            new RestTemplate().exchange(
                    otherMicroserviceUrl,
                    HttpMethod.POST,
                    requestEntity,
                    responseType
            );
        } catch (Exception ex) {
            throw new ClientResponseException(ex.getMessage());
        }
    }


}
