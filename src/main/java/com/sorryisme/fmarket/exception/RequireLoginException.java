package com.sorryisme.fmarket.exception;

public class RequireLoginException extends RuntimeException {
    public RequireLoginException(String message) {
        super(message);
    }
}
