package com.cryptoexchange.account.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private BigDecimal balance;
    private Boolean isActive;
    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
    private List<AccountTransaction> transactionList;
}
