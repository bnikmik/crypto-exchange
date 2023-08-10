package com.cryptoexchange.common.exception.types;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccountBadRequestException extends RuntimeException{
    public AccountBadRequestException(String message) {
        super(message);
    }
}
