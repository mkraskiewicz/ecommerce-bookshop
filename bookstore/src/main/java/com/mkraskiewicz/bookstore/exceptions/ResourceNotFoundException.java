package com.mkraskiewicz.bookstore.exceptions;

public class ResourceNotFoundException extends  RuntimeException {

    public ResourceNotFoundException() { }

    public ResourceNotFoundException(String message) {

        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause){

        super(message,cause);
    }

    public ResourceNotFoundException(String message, Throwable cause,
                                     boolean enableSuppresion, boolean writableStackTrace) {
        super(message,cause,enableSuppresion,writableStackTrace);
    }
}
