package com.cryptoexchange.auction.service.impl;

import com.cryptoexchange.auction.service.AccountClientService;
import com.cryptoexchange.common.dto.AccountDTO;
import com.cryptoexchange.common.exception.ResponseWrapper;
import com.cryptoexchange.common.exception.types.ClientResponseException;
import com.cryptoexchange.common.keycloak.KeycloakTokenService;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountClientServiceImpl implements AccountClientService {

    private final KeycloakTokenService keycloakTokenService;

    @Override
    public List<AccountDTO> findAllAccountsByCustomerId(UUID id) {

        String otherMicroserviceUrl = "http://localhost:8082/accounts/customer/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + keycloakTokenService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<ResponseWrapper<List<AccountDTO>>> responseEntity;
        try {
            responseEntity = new RestTemplate().exchange(
                    otherMicroserviceUrl,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );
            return responseEntity.getBody().getData();
        } catch (HttpClientErrorException ex) {
            throw new ClientResponseException(ex.getMessage(), ex);
        }
    }
}
