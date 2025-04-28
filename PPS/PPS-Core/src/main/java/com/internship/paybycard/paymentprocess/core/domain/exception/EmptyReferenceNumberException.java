package com.internship.paybycard.paymentprocess.core.domain.exception;

public class EmptyReferenceNumberException extends RuntimeException {
    public EmptyReferenceNumberException(String message) {
        super(message);
    }
}
