package com.cryptoexchange.auction.service.impl;

import com.cryptoexchange.auction.dto.AuctionDTO;
import com.cryptoexchange.auction.service.DealClientService;
import com.cryptoexchange.common.dto.DealDTO;
import com.cryptoexchange.common.exception.ResponseWrapper;
import com.cryptoexchange.common.exception.types.ClientResponseException;
import com.cryptoexchange.common.keycloak.CustomClaimsService;
import com.cryptoexchange.common.keycloak.KeycloakTokenService;
import com.cryptoexchange.common.model.DealType;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DealClientServiceImpl implements DealClientService {

    private final KeycloakTokenService keycloakTokenService;
    private final CustomClaimsService claimsService;

    @Override
    public DealDTO startDeal(AuctionDTO auctionDTO) {
        String otherMicroserviceUrl = "http://localhost:8083/deals";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + keycloakTokenService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        DealDTO dealDTO = new DealDTO();
        dealDTO.setSellerId(auctionDTO.getSellerId());
        dealDTO.setBalance(auctionDTO.getAmountCoins());
        dealDTO.setCurrency(auctionDTO.getCurrency());
        dealDTO.setDealType(DealType.P2P);
        dealDTO.setBuyerId(UUID.fromString(claimsService.getLoggedUserId()));
//        dealDTO.setGuarantorId()

        HttpEntity<DealDTO> requestEntity = new HttpEntity<>(dealDTO, headers);

        ResponseEntity<ResponseWrapper<DealDTO>> responseEntity;
        try {
            responseEntity = new RestTemplate().exchange(
                    otherMicroserviceUrl,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    });
        } catch (Exception ex) {
            throw new ClientResponseException(ex.getMessage());
        }

        return responseEntity.getBody().getData();
    }
}
