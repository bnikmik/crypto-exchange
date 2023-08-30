package com.cryptoexchange.customer.service.impl;

import com.cryptoexchange.common.dto.AccountDTO;
import com.cryptoexchange.common.exception.ResponseWrapper;
import com.cryptoexchange.common.exception.types.ClientResponseException;
import com.cryptoexchange.common.keycloak.KeycloakTokenService;
import com.cryptoexchange.common.model.Currency;
import com.cryptoexchange.customer.service.AccountClientService;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountClientServiceImpl implements AccountClientService {

    private final KeycloakTokenService keycloakTokenService;

    public AccountDTO createAccount(Currency currency, UUID customer_id) {

        String otherMicroserviceUrl = "http://localhost:8082/accounts";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + keycloakTokenService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setCurrency(currency);
        accountDTO.setCustomerId(customer_id);

        HttpEntity<AccountDTO> requestEntity = new HttpEntity<>(accountDTO, headers);

        ResponseEntity<ResponseWrapper<AccountDTO>> responseEntity;
        try {
            responseEntity = new RestTemplate().exchange(
                    otherMicroserviceUrl,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );
        } catch (HttpClientErrorException ex) {
            throw new ClientResponseException(ex.getMessage(), ex);
        }

        return responseEntity.getBody().getData();
    }
}
