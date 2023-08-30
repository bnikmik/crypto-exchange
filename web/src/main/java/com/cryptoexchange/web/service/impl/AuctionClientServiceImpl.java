package com.cryptoexchange.web.service.impl;

import com.cryptoexchange.common.dto.AuctionDTO;
import com.cryptoexchange.common.dto.AuctionStartDTO;
import com.cryptoexchange.common.exception.ResponseWrapper;
import com.cryptoexchange.common.exception.types.ClientResponseException;
import com.cryptoexchange.common.keycloak.CustomClaimsService;
import com.cryptoexchange.common.keycloak.KeycloakTokenService;
import com.cryptoexchange.common.model.Currency;
import com.cryptoexchange.web.service.AuctionClientService;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.cryptoexchange.web.WebApplication.restTemplate;

@Service
@AllArgsConstructor
public class AuctionClientServiceImpl implements AuctionClientService {

    private final KeycloakTokenService keycloakTokenService;
    private final CustomClaimsService claimsService;

    @Override
    public void createAuction(Currency currency, BigDecimal amount, BigDecimal pricePerUnit) {

        String otherMicroserviceUrl = "http://localhost:8090/auctions";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + keycloakTokenService.getToken());

        headers.setContentType(MediaType.APPLICATION_JSON);

        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setSellerId(UUID.fromString(claimsService.getLoggedUserId()));
        auctionDTO.setCurrency(currency);
        auctionDTO.setAmountCoins(amount);
        auctionDTO.setCoinPrice(pricePerUnit);

        HttpEntity<AuctionDTO> requestEntity = new HttpEntity<>(auctionDTO, headers);

        try {
            restTemplate().exchange(
                    otherMicroserviceUrl,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<ResponseWrapper<AuctionDTO>>() {
                    }
            );
        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<String> findAllAuctions() {

        String otherMicroserviceUrl = "http://localhost:8090/auctions";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + keycloakTokenService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        List<AuctionDTO> allAuctions;

        ResponseEntity<ResponseWrapper<List<AuctionDTO>>> responseEntity;
        try {
            responseEntity = new RestTemplate().exchange(
                    otherMicroserviceUrl,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );
            allAuctions = responseEntity.getBody().getData();
        } catch (HttpClientErrorException ex) {
            allAuctions = Collections.emptyList();
        }

        List<String> auctionList = new ArrayList<>();

        for (AuctionDTO auctionDTO : allAuctions) {
            if (!auctionDTO.getSellerId().equals(UUID.fromString(claimsService.getLoggedUserId()))) {
                auctionList.add("Счет: " + auctionDTO.getId() +
                        " Тип: " + auctionDTO.getCurrency() +
                        " Всего монет: " + auctionDTO.getAmountCoins() +
                        " Цена за единицу: " + auctionDTO.getCoinPrice());
            }
        }
        return auctionList;
    }

    @Override
    public AuctionDTO findAuctionById(UUID id) {
        String otherMicroserviceUrl = "http://localhost:8090/auctions/" + id;
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + keycloakTokenService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<ResponseWrapper<AuctionDTO>> responseEntity;
        try {
            responseEntity = new RestTemplate().exchange(
                    otherMicroserviceUrl,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );
        } catch (HttpClientErrorException ex) {
            throw new ClientResponseException(ex.getMessage());
        }

        return responseEntity.getBody().getData();
    }

    @Override
    public UUID startAuctionDeal(UUID id, BigDecimal amountCoins) {

        String otherMicroserviceUrl = "http://localhost:8090/auctions/" + id;
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + keycloakTokenService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        AuctionStartDTO amountDTO = new AuctionStartDTO();
        amountDTO.setAmountCoins(amountCoins);
        amountDTO.setBuyerId(UUID.fromString(claimsService.getLoggedUserId()));


        HttpEntity<?> requestEntity = new HttpEntity<>(amountDTO, headers);

        ResponseEntity<ResponseWrapper<AuctionDTO>> responseEntity;
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

        List<UUID> dealsIds = responseEntity.getBody().getData().getDealsIds();
        return dealsIds.get(dealsIds.size() - 1);
    }
}
