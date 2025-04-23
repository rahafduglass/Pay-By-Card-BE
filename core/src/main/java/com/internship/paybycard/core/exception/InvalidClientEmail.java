package com.internship.paybycard.core.exception;

public class InvalidClientEmail extends RuntimeException {
    public InvalidClientEmail(String message) {
        super(message);
    }
}
