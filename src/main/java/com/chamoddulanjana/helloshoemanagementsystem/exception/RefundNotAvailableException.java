package com.chamoddulanjana.helloshoemanagementsystem.exception;

public class RefundNotAvailableException extends RuntimeException{
    public RefundNotAvailableException() {
        super();
    }

    public RefundNotAvailableException(String message) {
        super(message);
    }

    public RefundNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefundNotAvailableException(Throwable cause) {
        super(cause);
    }

    protected RefundNotAvailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
