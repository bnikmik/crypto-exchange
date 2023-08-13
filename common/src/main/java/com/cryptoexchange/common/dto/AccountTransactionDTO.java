package com.cryptoexchange.common.dto;

import com.cryptoexchange.common.model.TransactionType;
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
