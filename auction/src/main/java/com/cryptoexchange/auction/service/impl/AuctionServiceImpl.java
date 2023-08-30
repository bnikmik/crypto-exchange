package com.cryptoexchange.auction.service.impl;

import com.cryptoexchange.auction.dto.AuctionDTO;
import com.cryptoexchange.auction.dto.AuctionStartDTO;
import com.cryptoexchange.auction.model.Auction;
import com.cryptoexchange.auction.repository.AuctionRepository;
import com.cryptoexchange.auction.service.AccountClientService;
import com.cryptoexchange.auction.service.AuctionService;
import com.cryptoexchange.auction.service.DealClientService;
import com.cryptoexchange.common.dto.AccountDTO;
import com.cryptoexchange.common.dto.DealDTO;
import com.cryptoexchange.common.exception.types.AccountBadRequestException;
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
    private final AccountClientService accountClientService;

    @Override
    public AuctionDTO createAuction(AuctionDTO auctionDTO) {
        Auction auction = INSTANCE.toEntity(auctionDTO);

        List<AccountDTO> allAccountsByCustomerId = accountClientService.findAllAccountsByCustomerId(auctionDTO.getSellerId());

        AccountDTO accountDTO = allAccountsByCustomerId.stream()
                .filter(a -> a.getCurrency().equals(auctionDTO.getCurrency()))
                .findFirst().orElseThrow(() -> new RecordNotFoundException("Счет у продавца с валютой " + auctionDTO.getCurrency() + " не найден."));

        if (compareToBigDecimal(accountDTO.getBalance(), auction.getAmountCoins()))
            throw new AccountBadRequestException("У продавца с ID " + auction.getSellerId() +
                    " не достаточно средств на аккаунте ID" + auction.getId() + " для проведения сделки");

        repository.save(auction);
        return INSTANCE.toDTO(auction);
    }

    @Override
    public AuctionDTO startAuctionDeal(UUID id, AuctionStartDTO amountDTO) {
        Auction auction = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Аукцион с ID " + id + " не найден."));
        if (compareToBigDecimal(auction.getAmountCoins(), amountDTO.getAmountCoins()))
            throw new AccountBadRequestException("У продавца с ID " + auction.getSellerId() +
                    " не достаточно средств на аукционе ID" + auction.getId() + " для проведения сделки");
        DealDTO dealDTO = dealClientService.startDeal(INSTANCE.toDTO(auction), amountDTO);
        auction.setAmountCoins(auction.getAmountCoins().subtract(amountDTO.getAmountCoins()));
        auction.getDealsIds().add(dealDTO.getId());
        repository.save(auction);
        return INSTANCE.toDTO(auction);
    }

    private boolean compareToBigDecimal(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) < 0;
    }

    @Override
    public AuctionDTO cancelAuctionDeal(UUID id, AuctionStartDTO amountDTO) {
        Auction auction = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Аукцион с ID " + id + " не найден."));
        auction.setAmountCoins(auction.getAmountCoins().add(amountDTO.getAmountCoins()));
        return INSTANCE.toDTO(auction);
    }

    @Override
    public AuctionDTO findAuctionById(UUID id) {
        Auction auction = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Аукцион с ID " + id + " не найден."));
        return INSTANCE.toDTO(auction);
    }

    @Override
    public List<AuctionDTO> findAllAuctions() {
        return repository.findAll().stream().map(INSTANCE::toDTO).collect(Collectors.toList());
    }
}
