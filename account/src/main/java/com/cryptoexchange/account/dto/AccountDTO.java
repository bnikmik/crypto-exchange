package com.cryptoexchange.account.dto;

import com.cryptoexchange.account.model.AccountTransaction;
import com.cryptoexchange.account.model.Currency;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AccountDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @NotNull
    private Currency currency;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal balance = new BigDecimal(0);
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isActive = true;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<AccountTransaction> transactionList = Collections.emptyList();
}
