package com.cryptoexchange.deal.service.impl;

import com.cryptoexchange.common.dto.AccountDTO;
import com.cryptoexchange.common.dto.CustomerDTO;
import com.cryptoexchange.common.model.Role;
import com.cryptoexchange.common.exception.types.AccountBadRequestException;
import com.cryptoexchange.common.exception.types.InsufficientRightsException;
import com.cryptoexchange.common.exception.types.RecordNotFoundException;
import com.cryptoexchange.deal.dto.DealDTO;
import com.cryptoexchange.deal.dto.DealStatusDTO;
import com.cryptoexchange.deal.model.Deal;
import com.cryptoexchange.deal.repository.DealRepository;
import com.cryptoexchange.deal.service.AccountClientService;
import com.cryptoexchange.deal.service.CustomerClientService;
import com.cryptoexchange.deal.service.DealService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static com.cryptoexchange.deal.mapping.DealMapping.INSTANCE;

@Service
@AllArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository repository;
    private final CustomerClientService customerClientService;
    private final AccountClientService accountClientService;

    @Override
    public DealDTO createNewDeal(DealDTO dealDTO) {
        Deal deal = INSTANCE.toEntity(dealDTO);

        CustomerDTO buyer = customerClientService.findCustomerById(deal.getBuyerId());
//        if (!buyer.getCustomerAccounts().containsKey(deal.getCurrency())) throw
        AccountDTO buyerAccount = accountClientService
                .findAccountById(buyer.getCustomerAccounts().get(deal.getCurrency()))
                .orElseThrow(() -> new AccountBadRequestException("У пользователя с ID " + buyer.getId() +
                        " счет с валютой " + deal.getCurrency() + " не найден"));

        CustomerDTO seller = customerClientService.findCustomerById(deal.getSellerId());
        AccountDTO sellerAccount = accountClientService
                .findAccountById(seller.getCustomerAccounts().get(deal.getCurrency()))
                .orElseThrow(() -> new AccountBadRequestException("У пользователя с ID " + seller.getId() +
                        " счет с валютой " + deal.getCurrency() + " не найден"));
        if (compareToBigDecimal(sellerAccount.getBalance(), deal.getBalance()))
            throw new AccountBadRequestException("У продавца с ID " + seller.getId() + " не достаточно средств на счете " + deal.getCurrency() + " для проведения сделки");

        CustomerDTO guarantor = customerClientService.findCustomerById(deal.getGuarantorId());
        if (!guarantor.getRolesList().contains(Role.MODERATOR))
            throw new InsufficientRightsException("У пользователя с ID " + guarantor.getId() +
                    " не достаточно прав, чтобы быть гарантом сделки");

        repository.save(deal);
        return INSTANCE.toDTO(deal);
    }

    private boolean compareToBigDecimal(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) < 0;
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
