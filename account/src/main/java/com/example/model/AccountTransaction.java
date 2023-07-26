package com.example.model;

import com.example.enums.TransactionType;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
public class AccountTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant createdAt = Instant.now();
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private BigDecimal value;
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
}
