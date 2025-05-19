package com.internship.paybycard.paymentprocess.core.domain.exception;

import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;

public class PersistenceException extends BusinessException {
    public PersistenceException(String message, ErrorCode errorCode) {
        super(message,errorCode);
    }
}
