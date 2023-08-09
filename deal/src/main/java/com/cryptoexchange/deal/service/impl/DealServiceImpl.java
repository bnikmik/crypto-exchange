package com.cryptoexchange.deal.service.impl;

import com.cryptoexchange.common.dto.CustomerDTO;
import com.cryptoexchange.common.dto.Role;
import com.cryptoexchange.common.exception.types.AccountExistsException;
import com.cryptoexchange.common.exception.types.InsufficientRightsException;
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

        CustomerDTO buyer = customerClientService.findCustomerById(deal.getBuyerId());
        if (!buyer.getCustomerAccounts().containsKey(deal.getCurrency()))
            throw new AccountExistsException("У пользователя с ID " + buyer.getId() + " счет с валютой " + deal.getCurrency() + " не найден");

        CustomerDTO seller = customerClientService.findCustomerById(deal.getSellerId());
        if (!seller.getCustomerAccounts().containsKey(deal.getCurrency()))
            throw new AccountExistsException("У пользователя с ID " + seller.getId() + " счет с валютой " + deal.getCurrency() + " не найден");

        CustomerDTO guarantor = customerClientService.findCustomerById(deal.getGuarantorId());
        if (!guarantor.getRolesList().contains(Role.MODERATOR)
                || !guarantor.getRolesList().contains(Role.ADMIN))
            throw new InsufficientRightsException("У пользователя с ID " + guarantor.getId() + " не достаточно прав, чтобы быть гарантом сделки");

        repository.save(deal);
        return INSTANCE.toDTO(deal);
    }

    @Override
    public DealDTO updateDealStatusById(UUID dealId, DealStatusDTO dealStatusDTO) {
        Deal deal = repository.findById(dealId).orElseThrow(() -> new RecordNotFoundException("Сделка с ID " + dealId + " не найдена"));
        Deal tmp = INSTANCE.toNewStatusEntity(dealStatusDTO);
        deal.setDealStatus(tmp.getDealStatus());
        Map<DealStatus, Instant> dealStatusTime = deal.getDealStatusTime();
        dealStatusTime.put(deal.getDealStatus(), Instant.now());
        deal.setDealStatusTime(dealStatusTime);
        repository.save(deal);
        return INSTANCE.toDTO(deal);
    }
}
