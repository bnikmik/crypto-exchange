package com.cryptoexchange.customer.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AccountDTO {
    private UUID id;
    private String currency;
    private BigDecimal balance;
    private Instant lastTransactionDate;
    private Boolean isActive;
    private List<String> transactionList;
}
