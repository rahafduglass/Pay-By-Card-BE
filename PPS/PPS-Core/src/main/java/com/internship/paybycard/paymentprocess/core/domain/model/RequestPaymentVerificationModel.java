package com.internship.paybycard.paymentprocess.core.domain.model;

public interface RequestPaymentVerificationModel {
    void verifyPayment();
    void sendOtp();

    String getReferenceNumber();
}
