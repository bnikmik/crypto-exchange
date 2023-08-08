package com.cryptoexchange.deal.dto;

import com.cryptoexchange.deal.model.DealStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DealStatusDTO {
    @NotNull(message = "DealStatus не может быть незаполненным")
    private DealStatus dealStatus;
}
