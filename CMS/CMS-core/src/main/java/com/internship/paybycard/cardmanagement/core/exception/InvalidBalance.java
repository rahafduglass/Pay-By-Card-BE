package com.internship.paybycard.cardmanagement.core.exception;

public class InvalidBalance extends RuntimeException {
    public InvalidBalance(String message) {
        super(message);
    }
}
