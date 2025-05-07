package com.internship.paybycard.paymentprocess.core.domain.exception;

import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;

public abstract class BusinessException extends RuntimeException {


    private ErrorCode errorCode;

    public BusinessException(String message,ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
