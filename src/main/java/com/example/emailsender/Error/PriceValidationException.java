package com.example.emailsender.Error;

public class PriceValidationException extends RuntimeException {
    public PriceValidationException(String message) {
        super(message);
    }
}
