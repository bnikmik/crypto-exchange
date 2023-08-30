package com.cryptoexchange.auction.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class AuctionStartDTO {
    @Positive(message = "Количество монет должно быть положительным")
    private BigDecimal amountCoins;
    private UUID buyerId;
}
