package com.internship.paybycard.core.exception;

public class InvalidClientName extends RuntimeException {
    public InvalidClientName(String message) {
        super(message);
    }
}
