package com.cryptoexchange.customer.service.impl;

import com.cryptoexchange.common.keycloak.KeycloakTokenService;
import com.cryptoexchange.customer.dto.AccountDTO;
import com.cryptoexchange.customer.service.AccountClientService;
import net.minidev.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountClientServiceImpl implements AccountClientService {

    private final KeycloakTokenService keycloakTokenService = new KeycloakTokenService();

    public AccountDTO getDto() {

        String otherMicroserviceUrl = "http://localhost:8082/accounts/test";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + keycloakTokenService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("currency", "BTC");

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonObject.toString(), headers);

        ResponseEntity<AccountDTO> responseEntity = new RestTemplate().exchange(
                otherMicroserviceUrl,
                HttpMethod.POST,
                requestEntity,
                AccountDTO.class
        );
        System.out.println();

        return responseEntity.getBody();
    }
}
