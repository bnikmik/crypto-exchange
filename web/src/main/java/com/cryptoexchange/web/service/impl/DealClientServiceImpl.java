package com.cryptoexchange.web.service.impl;

import com.cryptoexchange.common.dto.DealDTO;
import com.cryptoexchange.common.exception.ResponseWrapper;
import com.cryptoexchange.common.exception.types.ClientResponseException;
import com.cryptoexchange.common.keycloak.KeycloakTokenService;
import com.cryptoexchange.common.model.DealStatus;
import com.cryptoexchange.web.service.DealClientService;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DealClientServiceImpl implements DealClientService {

    private final KeycloakTokenService keycloakTokenService;

    @Override
    public DealDTO findDealById(UUID id) {
        String otherMicroserviceUrl = "http://localhost:8090/deals/" + id;
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + keycloakTokenService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<ResponseWrapper<DealDTO>> responseEntity;
        try {
            responseEntity = new RestTemplate().exchange(
                    otherMicroserviceUrl,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );
        } catch (HttpClientErrorException ex) {
            throw new ClientResponseException(ex.getMessage(), ex);
        }
        return responseEntity.getBody().getData();
    }

    @Override
    public void updateDealById(UUID id, DealStatus dealStatus) {
        String otherMicroserviceUrl = "http://localhost:8090/deals/" + id;
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + keycloakTokenService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<DealStatus> requestEntity = new HttpEntity<>(dealStatus, headers);

        ResponseEntity<ResponseWrapper<DealDTO>> responseEntity;
        try {
            responseEntity = new RestTemplate().exchange(
                    otherMicroserviceUrl,
                    HttpMethod.PUT,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );
        } catch (HttpClientErrorException ex) {
            throw new ClientResponseException(ex.getMessage());
        }
    }
}
