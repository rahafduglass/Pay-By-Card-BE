package com.internship.paybycard.core.exception;

public class InvalidBalance extends RuntimeException {
    public InvalidBalance(String message) {
        super(message);
    }
}
