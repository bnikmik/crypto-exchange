package com.cryptoexchange.deal.service.impl;

import com.cryptoexchange.common.exception.types.RecordNotFoundException;
import com.cryptoexchange.deal.dto.DealDTO;
import com.cryptoexchange.deal.dto.DealStatusDTO;
import com.cryptoexchange.deal.model.Deal;
import com.cryptoexchange.deal.model.DealStatus;
import com.cryptoexchange.deal.repository.DealRepository;
import com.cryptoexchange.deal.service.CustomerClientService;
import com.cryptoexchange.deal.service.DealService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static com.cryptoexchange.deal.mapping.DealMapping.INSTANCE;

@Service
@AllArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository repository;

    private final CustomerClientService customerClientService;

    @Override
    public DealDTO createNewDeal(DealDTO dealDTO) {
        Deal deal = INSTANCE.toEntity(dealDTO);
        customerClientService.findCustomerById(deal.getBuyerId());
        repository.save(deal);
        return INSTANCE.toDTO(deal);
    }

    @Override
    public DealDTO updateDealStatusById(UUID dealId, DealStatusDTO dealStatusDTO) {
        Deal deal = repository.findById(dealId).orElseThrow(()-> new RecordNotFoundException("Сделка с ID " + dealId + " не найдена"));
        Deal tmp = INSTANCE.toNewStatusEntity(dealStatusDTO);
        deal.setDealStatus(tmp.getDealStatus());
        Map<DealStatus, Instant> dealStatusTime = deal.getDealStatusTime();
        dealStatusTime.put(deal.getDealStatus(), Instant.now());
        deal.setDealStatusTime(dealStatusTime);
        repository.save(deal);
        return INSTANCE.toDTO(deal);
    }
}
