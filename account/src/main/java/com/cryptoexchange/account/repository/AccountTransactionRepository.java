package com.cryptoexchange.account.repository;

import com.cryptoexchange.account.model.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, UUID> {

    @Query(value = """
            SELECT sum(at.value)
                    FROM account_transaction at
                    WHERE type LIKE 'DEPOSIT'
                      AND at.account_id = :accountId NOT IN (SELECT sum(at.value)
                                                    FROM account_transaction at
                                                    WHERE type LIKE 'WITHDRAWAL'
                                                      AND at.account_id = :accountId)""",nativeQuery = true)
    BigDecimal calcBalance(@Param("accountId") UUID accountId);
}
