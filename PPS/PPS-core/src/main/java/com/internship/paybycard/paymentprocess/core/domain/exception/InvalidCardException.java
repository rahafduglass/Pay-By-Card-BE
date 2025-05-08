package com.internship.paybycard.paymentprocess.core.domain.exception;

import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;


public class InvalidCardException extends BusinessException {
    public InvalidCardException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
