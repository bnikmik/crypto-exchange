package com.cryptoexchange.deal.service.impl;

import com.cryptoexchange.common.dto.AuctionAmountDTO;
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

        AuctionAmountDTO amountDTO = new AuctionAmountDTO();
        amountDTO.setAmountCoins(amount);

        HttpEntity<AuctionAmountDTO> requestEntity = new HttpEntity<>(amountDTO, headers);

        try {
            new RestTemplate().exchange(
                    otherMicroserviceUrl,
                    HttpMethod.DELETE,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );
        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
        }
    }
}
