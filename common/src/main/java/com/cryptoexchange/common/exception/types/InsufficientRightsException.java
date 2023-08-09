package com.cryptoexchange.common.exception.types;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InsufficientRightsException extends RuntimeException{
    public InsufficientRightsException(String message) {
        super(message);
    }
}
