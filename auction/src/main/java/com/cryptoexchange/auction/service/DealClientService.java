package com.cryptoexchange.auction.service;

import com.cryptoexchange.auction.dto.AuctionDTO;
import com.cryptoexchange.common.dto.DealDTO;

public interface DealClientService {

    DealDTO startDeal(AuctionDTO auctionDTO);


}
