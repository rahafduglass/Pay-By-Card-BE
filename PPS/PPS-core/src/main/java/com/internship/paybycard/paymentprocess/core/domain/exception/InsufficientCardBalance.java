package com.internship.paybycard.paymentprocess.core.domain.exception;

public class InsufficientCardBalance extends RuntimeException {
    public InsufficientCardBalance(String message) {
        super(message);
    }
}