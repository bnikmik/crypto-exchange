package com.cryptoexchange.auction.service.impl;

import com.cryptoexchange.auction.dto.AuctionDTO;
import com.cryptoexchange.auction.model.Auction;
import com.cryptoexchange.auction.repository.AuctionRepository;
import com.cryptoexchange.auction.service.AuctionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.cryptoexchange.auction.mapper.AuctionMapper.INSTANCE;

@Service
@AllArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository repository;

    @Override
    public AuctionDTO createAuction(AuctionDTO auctionDTO) {
        Auction auction = INSTANCE.toEntity(auctionDTO);
        repository.save(auction);
        return INSTANCE.toDTO(auction);
    }
}
