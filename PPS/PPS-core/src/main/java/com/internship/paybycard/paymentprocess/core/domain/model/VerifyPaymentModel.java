package com.internship.paybycard.paymentprocess.core.domain.model;

public interface VerifyPaymentModel {
    void verifyPayment();
    void sendOtp();
    String getReferenceNumber();
}
