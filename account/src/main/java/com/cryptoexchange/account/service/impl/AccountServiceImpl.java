package com.cryptoexchange.account.service.impl;

import com.cryptoexchange.account.dto.AccountDTO;
import com.cryptoexchange.account.dto.BalanceDTO;
import com.cryptoexchange.account.model.Account;
import com.cryptoexchange.account.repository.AccountRepository;
import com.cryptoexchange.account.service.AccountService;
import com.cryptoexchange.common.exception.types.RecordNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.cryptoexchange.account.mapper.AccountMapper.INSTANCE;

@Service
@AllArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = INSTANCE.toEntity(accountDTO);
        repository.save(account);
        return INSTANCE.toDTO(account);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDTO findAccountById(UUID id) {
        Account account = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Счет с ID " + id + " не найден"));
        return INSTANCE.toDTO(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDTO> findAllAccounts() {
        return repository.findAll().stream().map(INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> findAllAccountsByCustomerId(UUID customerId) {
        List<AccountDTO> collect = repository.findAccountsByCustomerId(customerId).stream().map(INSTANCE::toDTO).collect(Collectors.toList());
        System.out.println();
        return collect;

    }

    @Override
    public void deleteAccountById(UUID id) {
        Account account = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Счет с ID " + id + " не найден"));
        account.setIsActive(false);
        repository.save(account);
    }

    @Override
    public BalanceDTO getBalance(UUID id) {
        Account account = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Счет с ID " + id + " не найден"));
        return INSTANCE.toBalanceDTO(account);
    }
}
