package com.cryptoexchange.auction.service;

import com.cryptoexchange.auction.dto.AuctionAmountDTO;
import com.cryptoexchange.auction.dto.AuctionDTO;

import java.util.List;
import java.util.UUID;

public interface AuctionService {

    AuctionDTO createAuction(AuctionDTO auctionDTO);

    AuctionDTO startAuctionDeal(UUID id, AuctionAmountDTO amountDTO);

    AuctionDTO cancelAuctionDeal(UUID id, AuctionAmountDTO amountDTO);

    List<AuctionDTO> findAllAuctions();

}
