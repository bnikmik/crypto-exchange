package com.cryptoexchange.customer.service.impl;

import com.cryptoexchange.common.dto.AccountDTO;
import com.cryptoexchange.common.dto.Currency;
import com.cryptoexchange.common.exception.types.ClientResponseException;
import com.cryptoexchange.common.exception.ResponseWrapper;
import com.cryptoexchange.common.keycloak.KeycloakTokenService;
import com.cryptoexchange.customer.service.AccountClientService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountClientServiceImpl implements AccountClientService {

    private final KeycloakTokenService keycloakTokenService = new KeycloakTokenService();

    public AccountDTO createAccount(Currency currency) {

        String otherMicroserviceUrl = "http://localhost:8082/accounts";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + keycloakTokenService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setCurrency(currency);

        ParameterizedTypeReference<ResponseWrapper<AccountDTO>> responseType =
                new ParameterizedTypeReference<>(){};

        HttpEntity<AccountDTO> requestEntity = new HttpEntity<>(accountDTO, headers);

        ResponseEntity<ResponseWrapper<AccountDTO>> responseEntity;
        try {
            responseEntity = new RestTemplate().exchange(
                    otherMicroserviceUrl,
                    HttpMethod.POST,
                    requestEntity,
                    responseType
            );
        } catch (Exception ex) {
            throw new ClientResponseException(ex.getMessage());
        }

        return responseEntity.getBody().getData();
    }
}
