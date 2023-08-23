package com.cryptoexchange.deal.service;

import java.math.BigDecimal;
import java.util.UUID;

public interface AuctionClientService {

    void cancelAuctionDeal(UUID auctionId, BigDecimal amount);

}
