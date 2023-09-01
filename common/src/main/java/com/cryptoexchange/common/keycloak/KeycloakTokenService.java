package com.cryptoexchange.common.keycloak;

import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Component
public class KeycloakTokenService {

    String KEYCLOAK_URL = "http://localhost:8080/realms/crypto-exchange/protocol/openid-connect/token";
    String CLIENT_ID = "crypto-exchange-client";
    String CLIENT_SECRET = "n96b2H84sWgdq2WyTFai6w0MQ4Ck7tW7";

    private String accessToken;
    private String refreshToken;
    private Instant accessTokenExpirationTime;
    private Instant refreshTokenExpirationTime;


    public String getToken() {
        if (accessToken == null || accessTokenExpirationTime.isBefore(Instant.now())) {
            postAccessToken();
        }
        return accessToken;
    }

    public void postAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", CLIENT_ID);
        params.add("client_secret", CLIENT_SECRET);

        if (refreshToken != null && refreshTokenExpirationTime.isAfter(Instant.now())) {
            params.add("grant_type", "refresh_token");
            params.add("refresh_token", refreshToken);
        } else {
            params.add("grant_type", "password");
            params.add("username", "admin");
            params.add("password", "1234");
        }

        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(params, headers);
        ResponseEntity<AccessTokenResponse> tokenResponse = new RestTemplate()
                .postForEntity(KEYCLOAK_URL, tokenRequest, AccessTokenResponse.class);

        if (tokenResponse.getStatusCode().is2xxSuccessful()) {
            AccessTokenResponse response = tokenResponse.getBody();
            accessTokenExpirationTime = Instant.now().plus(275, ChronoUnit.SECONDS);
            refreshTokenExpirationTime = Instant.now().plus(1775, ChronoUnit.SECONDS);
            accessToken = Objects.requireNonNull(response).getToken();
            refreshToken = response.getRefreshToken();
        } else {
            throw new RuntimeException("Failed to obtain access token from Keycloak");
        }
    }
}
