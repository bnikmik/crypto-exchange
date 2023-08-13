package com.cryptoexchange.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DealStatus {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    STARTED,
    FUNDS_ON_HOLD, DONE, CANCELED
}
