package com.cryptoexchange.auction.service;

import com.cryptoexchange.auction.dto.AuctionDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AuctionService {

    AuctionDTO createAuction(AuctionDTO auctionDTO);

    AuctionDTO startDeal(UUID id, BigDecimal amount);

    List<AuctionDTO> findAllAuctions();

}
