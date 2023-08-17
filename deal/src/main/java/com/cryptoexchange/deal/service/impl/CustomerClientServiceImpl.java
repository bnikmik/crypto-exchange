package com.cryptoexchange.deal.service.impl;

import com.cryptoexchange.common.dto.CustomerDTO;
import com.cryptoexchange.common.exception.ResponseWrapper;
import com.cryptoexchange.common.exception.types.ClientResponseException;
import com.cryptoexchange.common.keycloak.KeycloakTokenService;
import com.cryptoexchange.deal.service.CustomerClientService;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerClientServiceImpl implements CustomerClientService {

    private final KeycloakTokenService keycloakTokenService;

    public CustomerDTO findCustomerById(UUID id) {

        String otherMicroserviceUrl = "http://localhost:8081/customers/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + keycloakTokenService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<ResponseWrapper<CustomerDTO>> responseEntity;
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
