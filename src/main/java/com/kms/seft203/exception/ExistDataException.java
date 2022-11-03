package com.kms.seft203.exception;


public class ExistDataException extends Exception{
    public ExistDataException() {
        super("Data is already exist");
    }
    public ExistDataException(String msg)
    {
        super(msg);
    }
}
