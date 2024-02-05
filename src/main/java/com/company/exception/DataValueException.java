package com.company.exception;

public class DataValueException extends RuntimeException{
    public DataValueException(String msg) {
        super(msg);
    }

    public DataValueException(Exception ex) {
        super(ex);
    }
}