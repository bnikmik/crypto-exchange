package com.cryptoexchange.deal.service.impl;

import com.cryptoexchange.common.dto.AccountDTO;
import com.cryptoexchange.common.dto.CustomerDTO;
import com.cryptoexchange.common.exception.types.AccountBadRequestException;
import com.cryptoexchange.common.exception.types.DealBadStatusException;
import com.cryptoexchange.common.exception.types.InsufficientRightsException;
import com.cryptoexchange.common.exception.types.RecordNotFoundException;
import com.cryptoexchange.common.model.Currency;
import com.cryptoexchange.common.model.DealStatus;
import com.cryptoexchange.common.model.Role;
import com.cryptoexchange.common.model.TransactionType;
import com.cryptoexchange.deal.dto.DealDTO;
import com.cryptoexchange.deal.model.Deal;
import com.cryptoexchange.deal.repository.DealRepository;
import com.cryptoexchange.deal.service.AccountClientService;
import com.cryptoexchange.deal.service.AuctionClientService;
import com.cryptoexchange.deal.service.CustomerClientService;
import com.cryptoexchange.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static com.cryptoexchange.deal.mapping.DealMapping.INSTANCE;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository repository;
    private final CustomerClientService customerClientService;
    private final AccountClientService accountClientService;
    private final AuctionClientService auctionClientService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${crypto-exchange.kafka.request-topic}")
    private String requestTopic;

    private void sendRequest(String message) {
        kafkaTemplate.send(requestTopic, message);
    }

    private void validateCustomerAccount(CustomerDTO customerDTO, Currency currency) {
        if (!customerDTO.getCustomerAccounts().containsKey(currency))
            throw new AccountBadRequestException("У пользователя с ID " + customerDTO.getId() +
                    " счет с валютой " + currency + " не найден");
    }

    private void validateAccountBalance(CustomerDTO customerDTO, Currency currency, BigDecimal balance) {
        AccountDTO sellerAccount = accountClientService
                .findAccountById(customerDTO.getCustomerAccounts().get(currency));

        if (compareToBigDecimal(sellerAccount.getBalance(), balance))
            throw new AccountBadRequestException("У продавца с ID " + customerDTO.getId() +
                    " не достаточно средств на счете " + currency + " для проведения сделки");
    }

    private void validateCustomerRole(CustomerDTO customerDTO) {
        if (!customerDTO.getRolesList().contains(Role.MODERATOR))
            throw new InsufficientRightsException("У пользователя с ID " + customerDTO.getId() +
                    " не достаточно прав, чтобы быть гарантом сделки");
    }

    @Override
    public DealDTO createNewDeal(DealDTO dealDTO) {
        Deal deal = INSTANCE.toEntity(dealDTO);

        CustomerDTO buyer = customerClientService.findCustomerById(deal.getBuyerId());
        validateCustomerAccount(buyer, deal.getCurrency());

        CustomerDTO seller = customerClientService.findCustomerById(deal.getSellerId());
        validateCustomerAccount(seller, deal.getCurrency());

        //TODO:заглушка на гаранте, нужно будет релизовать поиск гарантов онлайн
        CustomerDTO guarantor = customerClientService.findCustomerById(UUID.fromString("26e05254-7ec3-4889-a6ad-a484e7954efd"));
        validateCustomerRole(guarantor);

        validateAccountBalance(seller, deal.getCurrency(), deal.getBalance());

        accountClientService.makeTransaction(
                seller.getCustomerAccounts().get(deal.getCurrency()),
                TransactionType.WITHDRAWAL,
                deal.getBalance()
        );

        repository.save(deal);
        return INSTANCE.toDTO(deal);
    }

    private boolean compareToBigDecimal(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) < 0;
    }

    private void validateDealStatus(DealStatus dealStatus, DealStatus previous) {
        switch (dealStatus) {
            case STARTED, BUYER_CONFIRMED, DONE, CANCELED -> {
                if (!dealStatus.equals(previous))
                    throw new DealBadStatusException("Выберите верный статус для следующего этапа сделки!");
            }
        }
    }

    @Override
    public DealDTO updateDealStatusById(UUID dealId, DealStatus dealStatus) {
        if (dealStatus.equals(DealStatus.STARTED))
            throw new DealBadStatusException("Выберите верный статус для следующего этапа сделки!");
        Deal deal = repository.findById(dealId).orElseThrow(() -> new RecordNotFoundException("Сделка с ID " + dealId + " не найдена"));

        CustomerDTO buyer = customerClientService.findCustomerById(deal.getBuyerId());
        CustomerDTO seller = customerClientService.findCustomerById(deal.getSellerId());

        switch (dealStatus) {
            case BUYER_CONFIRMED -> {
                validateDealStatus(deal.getDealStatus(), DealStatus.STARTED);
                sendRequest(seller.getEmail() + ";" + deal.getId());
            }
            //TODO:НУЖЕН ПРОМЕЖУТОЧНЫЙ СТАТУС ЕСЛИ ДЕНЬГИ НЕ ПОЛУЧИЛ ИЛИ НЕ БЫЛИ ОТПРАВЛЕНЫ, ЧТОБЫ ВЫЗЫВАТЬ МОДЕРАТОРА
            case DONE -> {
                validateDealStatus(deal.getDealStatus(), DealStatus.BUYER_CONFIRMED);
                accountClientService.makeTransaction(
                        buyer.getCustomerAccounts().get(deal.getCurrency()),
                        TransactionType.DEPOSIT,
                        deal.getBalance()
                );
            }
            case CANCELED -> {
                validateDealStatus(deal.getDealStatus(), DealStatus.BUYER_CONFIRMED);
                accountClientService.makeTransaction(
                        seller.getCustomerAccounts().get(deal.getCurrency()),
                        TransactionType.DEPOSIT,
                        deal.getBalance()
                );
                auctionClientService.cancelAuctionDeal(deal.getAuctionId(), deal.getBalance());
            }
        }

        deal.setDealStatus(dealStatus);
        Map<DealStatus, Instant> dealStatusTime = deal.getDealStatusTime();
        dealStatusTime.put(deal.getDealStatus(), Instant.now());
        deal.setDealStatusTime(dealStatusTime);
        repository.save(deal);
        return INSTANCE.toDTO(deal);
    }

    @Override
    public DealDTO findDealById(UUID dealId) {
        return INSTANCE.toDTO(repository.findById(dealId).orElseThrow(() -> new RecordNotFoundException("Сделка с ID " + dealId + " не найдена")));
    }
}
