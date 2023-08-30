package com.cryptoexchange.common.exception.types;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientResponseException extends RuntimeException {
    public ClientResponseException(String message) {
        super(message);
    }

    public ClientResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
