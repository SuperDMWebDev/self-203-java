package com.kms.seft203.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNAUTHORIZED)
public class DataNotFoundException extends Exception {
    public DataNotFoundException(String message) {
        super(message);
    }
}
