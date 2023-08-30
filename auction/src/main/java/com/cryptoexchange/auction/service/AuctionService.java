package com.cryptoexchange.auction.service;

import com.cryptoexchange.auction.dto.AuctionDTO;
import com.cryptoexchange.auction.dto.AuctionStartDTO;

import java.util.List;
import java.util.UUID;

public interface AuctionService {

    AuctionDTO createAuction(AuctionDTO auctionDTO);

    AuctionDTO startAuctionDeal(UUID id, AuctionStartDTO amountDTO);

    AuctionDTO cancelAuctionDeal(UUID id, AuctionStartDTO amountDTO);

    AuctionDTO findAuctionById(UUID uuid);

    List<AuctionDTO> findAllAuctions();

}
