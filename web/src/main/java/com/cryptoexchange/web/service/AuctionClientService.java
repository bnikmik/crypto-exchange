package com.cryptoexchange.web.service;

import com.cryptoexchange.common.dto.AuctionDTO;
import com.cryptoexchange.common.model.Currency;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AuctionClientService {
    void createAuction(Currency currency, BigDecimal amount, BigDecimal pricePerUnit);

    List<String> findAllAuctions();

    AuctionDTO findAuctionById(UUID id);

    UUID startAuctionDeal(UUID id, BigDecimal amountCoins);
}
