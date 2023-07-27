package com.cryptoexchange.account.service;

import com.cryptoexchange.account.dto.AccountTransactionDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountTransactionService {

    AccountTransactionDTO findAccountTransactionById(UUID id);

    List<AccountTransactionDTO> findAllAccountTransactions();

    UUID deposit(UUID accountId, BigDecimal value);

    UUID withdrawal(UUID accountId, BigDecimal value);

    BigDecimal getBalance(UUID accountId);
}
