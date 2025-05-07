package com.internship.paybycard.paymentprocess.core.domain.exception;

import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;

public class InvalidOtpException extends BusinessException {

    public InvalidOtpException(String message, ErrorCode errorCode) {
        super(message,errorCode);
    }




}
