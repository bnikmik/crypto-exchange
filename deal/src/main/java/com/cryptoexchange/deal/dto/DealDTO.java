package com.cryptoexchange.deal.dto;


import com.cryptoexchange.common.model.Currency;
import com.cryptoexchange.common.model.DealStatus;
import com.cryptoexchange.common.model.DealType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class DealDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @NotNull(message = "DealType не может быть незаполненным")
    private DealType dealType;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private DealStatus dealStatus = DealStatus.STARTED;
    @NotNull(message = "Currency не может быть незаполненным")
    private Currency currency;
    @Positive
    private BigDecimal balance;
    private UUID sellerId;
    private UUID buyerId;
    private UUID guarantorId;
    private UUID auctionId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Map<DealStatus, Instant> dealStatusTime = new HashMap<>() {{
        put(dealStatus, Instant.now());
    }};
}
