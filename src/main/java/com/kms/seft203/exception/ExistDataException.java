package com.kms.seft203.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN)
public class ExistDataException extends Exception{
    public ExistDataException() {
        super("Data is already exist");
    }
    public ExistDataException(String msg)
    {
        super(msg);
    }
}
