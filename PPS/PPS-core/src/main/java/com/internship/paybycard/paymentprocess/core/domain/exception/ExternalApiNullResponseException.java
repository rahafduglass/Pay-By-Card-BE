package com.internship.paybycard.paymentprocess.core.domain.exception;

import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;

public class ExternalApiNullResponseException extends BusinessException {
    public ExternalApiNullResponseException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
