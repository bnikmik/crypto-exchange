package com.cryptoexchange.common.dto;

import com.cryptoexchange.common.model.Currency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AuctionDTO {
    private UUID id;
    private UUID sellerId;
    private Currency currency;
    private BigDecimal coinPrice;
    private BigDecimal amountCoins;
    private List<UUID> dealsIds = Collections.emptyList();
}
