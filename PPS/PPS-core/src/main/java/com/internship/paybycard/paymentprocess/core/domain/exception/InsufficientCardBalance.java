package com.internship.paybycard.paymentprocess.core.domain.exception;

import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;

public class InsufficientCardBalance extends BusinessException {



    public InsufficientCardBalance(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}