package com.cryptoexchange.web.service.impl;

import com.cryptoexchange.common.dto.AccountDTO;
import com.cryptoexchange.common.exception.ResponseWrapper;
import com.cryptoexchange.common.exception.types.ClientResponseException;
import com.cryptoexchange.common.keycloak.KeycloakTokenService;
import com.cryptoexchange.web.service.AccountClientService;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountClientServiceImpl implements AccountClientService {

    private final KeycloakTokenService keycloakTokenService;


    public List<AccountDTO> findAllAccountsByCustomerId(String id) {

        String otherMicroserviceUrl = "http://localhost:8090/accounts/customer/" + id;
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
        } catch (Exception ex) {
            throw new ClientResponseException(ex.getMessage());
        }
        return responseEntity.getBody().getData();
    }

}
