package com.cryptoexchange.account.service.impl;

import com.cryptoexchange.account.dto.AccountTransactionDTO;
import com.cryptoexchange.account.model.Account;
import com.cryptoexchange.account.model.AccountTransaction;
import com.cryptoexchange.account.model.TransactionType;
import com.cryptoexchange.account.repository.AccountRepository;
import com.cryptoexchange.account.repository.AccountTransactionRepository;
import com.cryptoexchange.account.service.AccountTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.cryptoexchange.account.mapper.AccountTransactionMapper.INSTANCE;

@Service
@AllArgsConstructor
public class AccountTransactionServiceImpl implements AccountTransactionService {

    private final AccountTransactionRepository repository;
    private final AccountRepository accountRepository;

    @Override
    public AccountTransactionDTO findAccountTransactionById(UUID id) {
        AccountTransaction accountTransaction = repository.findById(id).orElseThrow();
        return INSTANCE.toDTO(accountTransaction);
    }

    @Override
    public List<AccountTransactionDTO> findAllAccountTransactions() {
        return repository.findAll().stream().map(INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UUID deposit(UUID accountId, AccountTransactionDTO accountTransactionDTO) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        AccountTransaction accountTransaction = INSTANCE.toEntity(accountTransactionDTO);
        accountTransaction.setType(TransactionType.DEPOSIT);
        accountTransaction.setAccount(account);
        repository.save(accountTransaction);
        return accountTransaction.getId();
    }

    @Override
    @Transactional
    public UUID withdrawal(UUID accountId, AccountTransactionDTO accountTransactionDTO) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        AccountTransaction accountTransaction = INSTANCE.toEntity(accountTransactionDTO);
        accountTransaction.setType(TransactionType.WITHDRAWAL);
        accountTransaction.setAccount(account);
        repository.save(accountTransaction);
        return accountTransaction.getId();
    }

    @Override
    public BigDecimal getBalance(UUID accountId) {
        return null;
    }
}