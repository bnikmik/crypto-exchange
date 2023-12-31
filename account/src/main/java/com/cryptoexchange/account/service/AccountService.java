package com.cryptoexchange.account.service;

import com.cryptoexchange.account.dto.AccountDTO;
import com.cryptoexchange.account.dto.BalanceDTO;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountDTO createAccount(AccountDTO accountDTO);

    AccountDTO findAccountById(UUID id);

    List<AccountDTO> findAllAccounts();

    List<AccountDTO> findAllAccountsByCustomerId(UUID customerId);

    void deleteAccountById(UUID id);

    BalanceDTO getBalance(UUID id);
}
