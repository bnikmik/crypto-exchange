package com.cryptoexchange.auction.service.impl;

import com.cryptoexchange.auction.dto.AuctionDTO;
import com.cryptoexchange.auction.model.Auction;
import com.cryptoexchange.auction.repository.AuctionRepository;
import com.cryptoexchange.auction.service.AuctionService;
import com.cryptoexchange.auction.service.DealClientService;
import com.cryptoexchange.common.dto.DealDTO;
import com.cryptoexchange.common.exception.types.RecordNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.cryptoexchange.auction.mapper.AuctionMapper.INSTANCE;

@Service
@AllArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository repository;
    private final DealClientService dealClientService;

    @Override
    public AuctionDTO createAuction(AuctionDTO auctionDTO) {
        Auction auction = INSTANCE.toEntity(auctionDTO);
        repository.save(auction);
        return INSTANCE.toDTO(auction);
    }

    @Override
    public AuctionDTO startDeal(UUID id, BigDecimal amount) {
        Auction auction = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Аукцион с ID " + id + " не найден."));
        DealDTO dealDTO = dealClientService.startDeal(INSTANCE.toDTO(auction));
        auction.getDealsIds().add(dealDTO.getId());
        repository.save(auction);
        return INSTANCE.toDTO(auction);
    }

    @Override
    public List<AuctionDTO> findAllAuctions() {
        return repository.findAll().stream().map(INSTANCE::toDTO).collect(Collectors.toList());
    }
}
