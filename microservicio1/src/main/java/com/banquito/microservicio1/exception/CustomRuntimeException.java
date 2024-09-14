package com.banquito.microservicio1.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomRuntimeException extends RuntimeException {
    private final HttpStatus code;

    public CustomRuntimeException(String msg, HttpStatus code) {
        super(msg);
        this.code = code;
    }
}


