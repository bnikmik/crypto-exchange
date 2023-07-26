package com.example.dto;

import com.example.model.AccountTransaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AccountDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotBlank
    private String accountNumber;
    @NotBlank
    private String currencyType;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal balance;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<AccountTransaction> transactionList;
}
