package com.cryptoexchange.account.service;

import com.cryptoexchange.account.dto.AccountDTO;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountDTO createAccount(AccountDTO accountDTO);

    AccountDTO findAccount(UUID id);

    List<AccountDTO> findAllAccounts();

    void deleteAccountById(UUID id);
}
