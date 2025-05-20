package com.internship.paybycard.paymentprocess.core.domain.result;

public enum ErrorCode {
    SUCCESS,
    NULL,
    INTERNAL_SERVER_ERROR,
    EXTERNAL_API_NULL_RESPONSE,
    INVALID_CARD,
    PAYMENT_NOT_FOUND,
    INSUFFICIENT_CARD_BALANCE,
    INVALID_OR_EXPIRED_OTP, INVALID_CREDENTIALS, INVALID_OR_EXPIRED_TOKEN,

}
