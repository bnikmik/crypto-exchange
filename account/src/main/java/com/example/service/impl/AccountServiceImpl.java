package com.example.service.impl;

import com.example.dto.AccountDTO;
import com.example.model.Account;
import com.example.repository.AccountRepository;
import com.example.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.mapper.AccountMapper.INSTANCE;

@Service
@AllArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        repository.save(INSTANCE.toEntity(accountDTO));
        return accountDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDTO findAccount(Long id) {
        Account account = repository.findById(id).orElseThrow();
        return INSTANCE.toDTO(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDTO> findAllAccounts() {
        return repository.findAll().stream().map(INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public AccountDTO updateAccountById(Long id, AccountDTO accountDTO) {
        Account tmp = repository.findById(id).orElseThrow();
        //TODO:уточнить по методу
        return INSTANCE.toDTO(tmp);
    }

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
