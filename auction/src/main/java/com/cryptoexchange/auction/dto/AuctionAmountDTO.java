package com.cryptoexchange.auction.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
public class AuctionAmountDTO {
    @Positive(message = "Количество монет должно быть положительным")
    private BigDecimal amountCoins;
}
