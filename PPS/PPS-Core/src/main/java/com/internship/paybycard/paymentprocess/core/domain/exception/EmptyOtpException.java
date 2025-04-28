package com.internship.paybycard.paymentprocess.core.domain.exception;

public class EmptyOtpException extends RuntimeException {
    public EmptyOtpException(String message) {
        super(message);
    }
}
