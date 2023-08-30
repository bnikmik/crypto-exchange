package com.cryptoexchange.deal.service.impl;

import com.cryptoexchange.common.dto.AuctionStartDTO;
import com.cryptoexchange.common.exception.types.ClientResponseException;
import com.cryptoexchange.common.keycloak.KeycloakTokenService;
import com.cryptoexchange.deal.service.AuctionClientService;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuctionClientServiceImpl implements AuctionClientService {

    private final KeycloakTokenService keycloakTokenService;

    @Override
    public void cancelAuctionDeal(UUID auctionId, BigDecimal amount) {

        String otherMicroserviceUrl = "http://localhost:8084/auctions/" + auctionId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + keycloakTokenService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        AuctionStartDTO amountDTO = new AuctionStartDTO();
        amountDTO.setAmountCoins(amount);

        HttpEntity<AuctionStartDTO> requestEntity = new HttpEntity<>(amountDTO, headers);

        try {
            new RestTemplate().exchange(
                    otherMicroserviceUrl,
                    HttpMethod.DELETE,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );
        } catch (HttpClientErrorException ex) {
            throw new ClientResponseException(ex.getMessage(), ex);
        }
    }
}
