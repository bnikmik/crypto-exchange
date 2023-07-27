package com.cryptoexchange.account.service;

import com.cryptoexchange.account.dto.AccountDTO;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    AccountDTO createAccount(AccountDTO accountDTO);

    AccountDTO findAccount(Long id);

    List<AccountDTO> findAllAccounts();

    AccountDTO updateAccountById(Long id, AccountDTO accountDTO);

    void deleteAccountById(Long id);
    //TODO:uuid на возрат
    void deposit(Long id, BigDecimal value);

    void withdrawal(Long id, BigDecimal value);

    BigDecimal getBalance(Long id);
}
