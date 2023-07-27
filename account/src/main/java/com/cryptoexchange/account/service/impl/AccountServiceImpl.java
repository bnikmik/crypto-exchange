package com.cryptoexchange.account.service.impl;

import com.cryptoexchange.account.mapper.AccountMapper;
import com.cryptoexchange.account.repository.AccountRepository;
import com.cryptoexchange.account.service.AccountService;
import com.cryptoexchange.account.dto.AccountDTO;
import com.cryptoexchange.account.model.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        repository.save(AccountMapper.INSTANCE.toEntity(accountDTO));
        return accountDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDTO findAccount(Long id) {
        Account account = repository.findById(id).orElseThrow();
        return AccountMapper.INSTANCE.toDTO(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDTO> findAllAccounts() {
        return repository.findAll().stream().map(AccountMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public AccountDTO updateAccountById(Long id, AccountDTO accountDTO) {
        Account tmp = repository.findById(id).orElseThrow();
        //TODO:уточнить по методу
        return AccountMapper.INSTANCE.toDTO(tmp);
    }

    //TODO:НЕ УДАЛЯТЬ
    @Override
    public void deleteAccountById(Long id) {
        Account account = repository.findById(id).orElseThrow();
        repository.delete(account);
    }

    @Override
    public void deposit(Long id, BigDecimal bigDecimal) {

    }

    @Override
    public void withdrawal(Long id, BigDecimal bigDecimal) {

    }

    @Override
    public BigDecimal getBalance(Long id) {
        return null;
    }
}
