package com.cryptoexchange.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DealStatus {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    STARTED,
    BUYER_CONFIRMED, SELLER_NOT_CONFIRMED, DONE, CANCELED
}
