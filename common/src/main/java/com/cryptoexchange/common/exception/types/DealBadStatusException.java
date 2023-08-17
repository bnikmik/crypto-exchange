package com.cryptoexchange.common.exception.types;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DealBadStatusException extends RuntimeException {
    public DealBadStatusException(String message) {
        super(message);
    }
}
