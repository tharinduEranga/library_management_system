package com.example.library.exception.custom;

public class BusinessRuleViolationException extends RuntimeException {
    public BusinessRuleViolationException() {
    }

    public BusinessRuleViolationException(String message) {
        super(message);
    }

    public BusinessRuleViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
