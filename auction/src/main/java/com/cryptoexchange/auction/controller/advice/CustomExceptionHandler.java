package com.cryptoexchange.auction.controller.advice;

import com.cryptoexchange.common.exception.ResponseWrapper;
import com.cryptoexchange.common.exception.types.RecordNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(
            RecordNotFoundException ex) {
        List<String> errorDetails = new ArrayList<>();
        errorDetails.add(ex.getLocalizedMessage());
        ResponseWrapper<?> error = new ResponseWrapper<>(Instant.now(), HttpStatus.NOT_FOUND, null, errorDetails);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorDetails = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            errorDetails.add(error.getDefaultMessage());
        }
        ResponseWrapper<?> error = new ResponseWrapper<>(Instant.now(), HttpStatus.BAD_REQUEST, null, errorDetails);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
