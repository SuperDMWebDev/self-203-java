package com.kms.seft203.auth.exception;


public class ExistDataException extends Exception{
    public ExistDataException() {
        super("Data is already exist");
    }
    public ExistDataException(String msg)
    {
        super(msg);
    }
}
