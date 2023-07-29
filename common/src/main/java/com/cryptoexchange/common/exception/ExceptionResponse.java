package com.cryptoexchange.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.Instant;
import java.util.List;

@XmlRootElement(name = "error")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor()
public class ExceptionResponse {
    private Instant timestamp;
    private HttpStatus status;
    private List<String> details;
}
