package com.cryptoexchange.auction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

public class AuctionDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    private Long sellerId;
    @NotNull(message = "Currency не может быть незаполненным")
    private Currency currency;
    @Positive(message = "Coin price должен быть больше 0")
    private BigDecimal coinPrice;
    @Positive(message = "Amount coins должен быть больше 0")
    private BigDecimal amountCoins;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<UUID> dealsIds = Collections.emptyList();
}
