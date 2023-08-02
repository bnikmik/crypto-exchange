package com.cryptoexchange.account.dto;

import com.cryptoexchange.account.model.TransactionType;
import com.cryptoexchange.common.validator.EnumNamePattern;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;
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
    @EnumNamePattern(regexp = "WITHDRAWAL|DEPOSIT", message = "Транзакция должна быть WITHDRAWAL или DEPOSIT")
    private TransactionType type;
    @Positive
    private BigDecimal value;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID account;
}
