package com.cryptoexchange.auction.service.impl;

import com.cryptoexchange.auction.dto.AuctionDTO;
import com.cryptoexchange.auction.dto.AuctionStartDTO;
import com.cryptoexchange.auction.service.DealClientService;
import com.cryptoexchange.common.dto.DealDTO;
import com.cryptoexchange.common.exception.ResponseWrapper;
import com.cryptoexchange.common.exception.types.ClientResponseException;
import com.cryptoexchange.common.keycloak.KeycloakTokenService;
import com.cryptoexchange.common.model.DealType;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class DealClientServiceImpl implements DealClientService {

    private final KeycloakTokenService keycloakTokenService;

    @Override
    public DealDTO startDeal(AuctionDTO auctionDTO, AuctionStartDTO amountDTO) {
        String otherMicroserviceUrl = "http://localhost:8083/deals";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + keycloakTokenService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        DealDTO dealDTO = new DealDTO();
        dealDTO.setSellerId(auctionDTO.getSellerId());
        dealDTO.setBalance(amountDTO.getAmountCoins());
        dealDTO.setCurrency(auctionDTO.getCurrency());
        dealDTO.setDealType(DealType.P2P);
        dealDTO.setBuyerId(amountDTO.getBuyerId());
        dealDTO.setAuctionId(auctionDTO.getId());

        HttpEntity<DealDTO> requestEntity = new HttpEntity<>(dealDTO, headers);

        ResponseEntity<ResponseWrapper<DealDTO>> responseEntity;
        try {
            responseEntity = new RestTemplate().exchange(
                    otherMicroserviceUrl,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    });
        } catch (HttpClientErrorException ex) {
            throw new ClientResponseException(ex.getMessage(), ex);
        }

        return responseEntity.getBody().getData();
    }
}
