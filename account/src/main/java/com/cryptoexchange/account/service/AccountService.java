package com.cryptoexchange.account.service;

import com.cryptoexchange.account.dto.AccountDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountDTO createAccount(AccountDTO accountDTO);

    AccountDTO findAccount(UUID id);

    List<AccountDTO> findAllAccounts();

    AccountDTO updateAccountById(UUID id, AccountDTO accountDTO);

    void deleteAccountById(UUID id);
    //TODO:uuid на возрат
    void deposit(UUID id, BigDecimal value);

    void withdrawal(UUID id, BigDecimal value);

    BigDecimal getBalance(UUID id);
}
