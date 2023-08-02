package com.cryptoexchange.account.service;

import com.cryptoexchange.account.dto.AccountTransactionDTO;
import com.cryptoexchange.account.dto.TransactionIdDTO;

import java.util.List;
import java.util.UUID;

public interface AccountTransactionService {

    AccountTransactionDTO findAccountTransactionById(UUID id);

    List<AccountTransactionDTO> findAllAccountTransactions();

    TransactionIdDTO makeTransaction(UUID accountId, AccountTransactionDTO accountTransactionDTO);
}
