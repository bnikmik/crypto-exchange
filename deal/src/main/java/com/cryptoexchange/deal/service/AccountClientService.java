package com.cryptoexchange.deal.service;

import com.cryptoexchange.common.dto.AccountDTO;
import com.cryptoexchange.common.model.TransactionType;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface AccountClientService {
    Optional<AccountDTO> findAccountById(UUID id);

    void makeTransaction(UUID id, TransactionType type, BigDecimal value);
}
