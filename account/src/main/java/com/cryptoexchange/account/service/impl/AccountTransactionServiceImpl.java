package com.cryptoexchange.account.service.impl;

import com.cryptoexchange.account.dto.AccountTransactionDTO;
import com.cryptoexchange.account.dto.TransactionIdDTO;
import com.cryptoexchange.account.model.Account;
import com.cryptoexchange.account.model.AccountTransaction;
import com.cryptoexchange.account.repository.AccountRepository;
import com.cryptoexchange.account.repository.AccountTransactionRepository;
import com.cryptoexchange.account.service.AccountTransactionService;
import com.cryptoexchange.common.exception.types.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.cryptoexchange.account.mapper.AccountTransactionMapper.INSTANCE;

@Service
@RequiredArgsConstructor
public class AccountTransactionServiceImpl implements AccountTransactionService {

    private final AccountTransactionRepository repository;
    private final AccountRepository accountRepository;

    @Override
    public AccountTransactionDTO findAccountTransactionById(UUID id) {
        AccountTransaction accountTransaction = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Транзакция с ID " + id + " не найдена"));
        return INSTANCE.toDTO(accountTransaction);
    }

    @Override
    public List<AccountTransactionDTO> findAllAccountTransactions() {
        return repository.findAll().stream().map(INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TransactionIdDTO makeTransaction(UUID accountId, AccountTransactionDTO accountTransactionDTO) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RecordNotFoundException("Счет с ID " + accountId + " не найден"));
        AccountTransaction accountTransaction = INSTANCE.toEntity(accountTransactionDTO);
        accountTransaction.setAccount(account);
        repository.save(accountTransaction);
        account.setBalance(calcBalance(accountId, account.getBalance(), account.getLastTransactionDate()));
        account.setLastTransactionDate(accountTransaction.getCreatedAt());
        accountRepository.save(account);
        return INSTANCE.toTransactionIdDTO(accountTransaction);
    }

    private BigDecimal calcBalance(UUID accountId, BigDecimal balance, Instant lastTransactionDate) {
        return repository.calcBalance(accountId, balance, lastTransactionDate);
    }
}
