package com.sparta.hanghaememo.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class RestApiException extends RuntimeException{
    private final HttpStatus httpStatus;

    public RestApiException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getHttpStatus();
    }
}
