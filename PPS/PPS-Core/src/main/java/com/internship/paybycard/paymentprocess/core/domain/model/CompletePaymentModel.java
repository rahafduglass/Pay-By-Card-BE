package com.internship.paybycard.paymentprocess.core.domain.model;

public interface CompletePaymentModel {
    void verifyOTP();
    void process();

    String getPaymentReference();
    String getOTP();

    void setPaymentReference(String paymentReference);
    void setOTP(String otp);
}
