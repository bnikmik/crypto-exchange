package com.cryptoexchange.account.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private BigDecimal balance;
    private Instant lastTransactionDate = Instant.EPOCH;
    private Boolean isActive;
    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
    private List<AccountTransaction> transactionList;
}
