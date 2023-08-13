package com.cryptoexchange.account.model;

import com.cryptoexchange.common.model.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
public class AccountTransaction {
    @Id
    @GeneratedValue
    private UUID id;
    private Instant createdAt = Instant.now();
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private BigDecimal value;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Account account;
}
