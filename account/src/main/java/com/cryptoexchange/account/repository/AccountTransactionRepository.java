package com.cryptoexchange.account.repository;

import com.cryptoexchange.account.model.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {
}
