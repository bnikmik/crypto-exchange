package com.cryptoexchange.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.Instant;

@XmlRootElement(name = "error")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor()
public class ResponseWrapper<T> {
    private Instant timestamp;
    private HttpStatus status;
    private T details;
}
