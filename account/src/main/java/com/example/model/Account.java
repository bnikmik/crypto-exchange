package com.example.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private String currencyType;
    private BigDecimal balance;
    @OneToMany(mappedBy = "account",cascade = CascadeType.REMOVE)
    private List<AccountTransaction> transactionList;
}
