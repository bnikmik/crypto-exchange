package com.cryptoexchange.account.service;

import com.cryptoexchange.account.dto.AccountTransactionDTO;
import com.cryptoexchange.account.dto.TransactionIdDTO;

import java.util.List;
import java.util.UUID;

public interface AccountTransactionService {

    AccountTransactionDTO findAccountTransactionById(UUID id);

    List<AccountTransactionDTO> findAllAccountTransactions();

    TransactionIdDTO deposit(UUID accountId, AccountTransactionDTO accountTransactionDTO);

    TransactionIdDTO withdrawal(UUID accountId, AccountTransactionDTO accountTransactionDTO);
}
