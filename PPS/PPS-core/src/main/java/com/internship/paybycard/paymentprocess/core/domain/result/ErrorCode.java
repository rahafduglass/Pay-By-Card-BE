package com.internship.paybycard.paymentprocess.core.domain.result;

public enum ErrorCode {
    NULL,
    INTERNAL_SERVER_ERROR,
    INVALID_COMMAND_INPUT,
    INVALID_PAYMENT_INPUT,
    INVALID_CARD,
    EMPTY_REFERENCE_NUMBER,
    PAYMENT_NOT_FOUND,
    INSUFFICIENT_CARD_BALANCE,
    EMPTY_OTP,
    INVALID_OR_EXPIRED_OTP,
}
