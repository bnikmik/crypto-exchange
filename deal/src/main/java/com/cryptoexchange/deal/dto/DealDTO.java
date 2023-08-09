package com.cryptoexchange.deal.dto;


import com.cryptoexchange.common.dto.Currency;
import com.cryptoexchange.deal.model.DealStatus;
import com.cryptoexchange.deal.model.DealType;
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
    private DealStatus dealStatus = DealStatus.NULL;
    @NotNull(message = "Currency не может быть незаполненным")
    private Currency currency;
    @Positive
    private BigDecimal balance;
    private Long sellerId;
    private Long buyerId;
    private Long guarantorId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Map<DealStatus, Instant> dealStatusTime = new HashMap<>() {{
        put(dealStatus, Instant.now());
    }};
}
