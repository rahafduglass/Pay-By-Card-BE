package com.internship.paybycard.paymentprocess.core.domain.exception;

import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;

public class PaymentNotFoundException extends BusinessException {
    public PaymentNotFoundException(String message, ErrorCode errorCode) {
        super(message,errorCode);
    }
}
