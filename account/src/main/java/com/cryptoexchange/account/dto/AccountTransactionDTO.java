package com.cryptoexchange.account.dto;

import com.cryptoexchange.account.model.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class AccountTransactionDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private TransactionType type;
    @PositiveOrZero
    private BigDecimal value;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID account;
}
