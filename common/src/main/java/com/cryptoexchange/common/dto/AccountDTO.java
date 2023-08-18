package com.cryptoexchange.common.dto;

import com.cryptoexchange.common.model.Currency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AccountDTO {
    private UUID id;
    private Currency currency;
    private BigDecimal balance = new BigDecimal(0);
    private Instant lastTransactionDate = Instant.EPOCH;
    private Boolean isActive = true;
    private UUID customerId;
    private List<AccountTransactionDTO> transactionList = Collections.emptyList();
}
