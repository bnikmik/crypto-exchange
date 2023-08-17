package com.cryptoexchange.web.service.impl;

import com.cryptoexchange.common.dto.CustomerDTO;
import com.cryptoexchange.common.exception.ResponseWrapper;
import com.cryptoexchange.common.keycloak.CustomClaimsService;
import com.cryptoexchange.common.keycloak.KeycloakTokenService;
import com.cryptoexchange.web.service.CustomerClientService;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import static com.cryptoexchange.web.WebApplication.restTemplate;

@Service
@AllArgsConstructor
public class CustomerClientServiceImpl implements CustomerClientService {

    private final KeycloakTokenService keycloakTokenService;
    private final CustomClaimsService claimsService;

    @Override
    public boolean checkCustomerRegistration(String id) {
        String otherMicroserviceUrl = "http://localhost:8081/customers/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + keycloakTokenService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<ResponseWrapper<CustomerDTO>> responseEntity = restTemplate().exchange(
                    otherMicroserviceUrl,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    });
            return responseEntity.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException ex) {
            return false;
        }
    }

    @Override
    public void createCustomer() {

        String otherMicroserviceUrl = "http://localhost:8081/customers";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + keycloakTokenService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        CustomerDTO customerDTO = claimsService.createCustomer();

        HttpEntity<CustomerDTO> requestEntity = new HttpEntity<>(customerDTO, headers);

        try {
            restTemplate().exchange(
                    otherMicroserviceUrl,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<ResponseWrapper<CustomerDTO>>() {
                    }
            );
        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
        }
    }
}
