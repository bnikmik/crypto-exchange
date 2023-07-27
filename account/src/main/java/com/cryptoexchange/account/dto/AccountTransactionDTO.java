package com.cryptoexchange.account.dto;

import com.cryptoexchange.account.model.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class AccountTransactionDTO {
    private UUID id;
    private Instant createdAt;
    private TransactionType type;
    private BigDecimal value;
    private UUID account;
}
