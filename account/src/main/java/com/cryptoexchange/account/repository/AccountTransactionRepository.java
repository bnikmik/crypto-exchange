package com.cryptoexchange.account.repository;

import com.cryptoexchange.account.model.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, UUID> {

    @Query(value = """
            SELECT ((SELECT COALESCE(sum(at.value),0)
                     FROM account_schema.account_transaction at
                     WHERE type LIKE 'DEPOSIT'
                       AND at.account_id = :accountId
                       AND at.created_at > :lastTransactionDate) - (SELECT COALESCE(sum(at.value),0)
                                                                    FROM account_schema.account_transaction at
                                                                    WHERE type LIKE 'WITHDRAWAL'
                                                                      AND at.account_id = :accountId
                                                                      AND at.created_at > :lastTransactionDate) +
                    :balance) as result
                        """, nativeQuery = true)
    BigDecimal calcBalance(@Param("accountId") UUID accountId, @Param("balance") BigDecimal balance, @Param("lastTransactionDate") Instant lastTransactionDate);
}
