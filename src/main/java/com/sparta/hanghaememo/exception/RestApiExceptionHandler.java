package com.sparta.hanghaememo.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {
    // Request Error Handler
    @ExceptionHandler(RestApiException.class)
    protected ResponseEntity<String> handleCustomException(RestApiException e) {

        log.debug("Exception : '{}'", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }
}