package com.internship.paybycard.paymentprocess.core.domain.model;

public interface CompletePaymentModel {
    void verifyOTP();
    void process();

    String getReferenceNumber();
    String getOTP();

    void setReferenceNumber(String paymentReference);
    void setOTP(String otp);
}
