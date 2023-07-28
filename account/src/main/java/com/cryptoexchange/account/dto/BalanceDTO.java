package com.cryptoexchange.account.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BalanceDTO {
    private BigDecimal balance;
}
