package com.cryptoexchange.account.dto;

import com.cryptoexchange.account.model.AccountTransaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AccountDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @NotBlank
    private String currencyType;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal balance;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<AccountTransaction> transactionList;
}
