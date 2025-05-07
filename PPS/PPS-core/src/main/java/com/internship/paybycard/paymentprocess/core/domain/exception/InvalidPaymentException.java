package com.internship.paybycard.paymentprocess.core.domain.exception;

// todo intreduce Bussiness Error Exception
public class InvalidPaymentException extends RuntimeException {
    public InvalidPaymentException(String message) {
        super(message);
    }
}
