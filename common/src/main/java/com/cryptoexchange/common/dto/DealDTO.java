package com.cryptoexchange.common.dto;


import com.cryptoexchange.common.model.Currency;
import com.cryptoexchange.common.model.DealStatus;
import com.cryptoexchange.common.model.DealType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class DealDTO {
    private UUID id;
    private DealType dealType;
    private DealStatus dealStatus = DealStatus.STARTED;
    private Currency currency;
    private BigDecimal balance;
    private UUID sellerId;
    private UUID buyerId;
    private UUID guarantorId;
    private Map<DealStatus, Instant> dealStatusTime;
}
