package com.cryptoexchange.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class AuctionStartDTO {
    private BigDecimal amountCoins;
    private UUID buyerId;
}
