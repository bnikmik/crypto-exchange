package com.cryptoexchange.account.service.impl;

import com.cryptoexchange.account.dto.AccountTransactionDTO;
import com.cryptoexchange.account.model.AccountTransaction;
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
    public UUID deposit(UUID accountId, BigDecimal value) {
        return null;
    }

    @Override
    @Transactional
    public UUID withdrawal(UUID accountId, BigDecimal value) {
        return null;
    }

    @Override
    public BigDecimal getBalance(UUID accountId) {
        return null;
    }
}
