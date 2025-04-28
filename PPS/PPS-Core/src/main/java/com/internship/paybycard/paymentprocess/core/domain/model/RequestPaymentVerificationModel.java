package com.internship.paybycard.paymentprocess.core.domain.model;

public interface RequestPaymentVerificationModel {
    void verifyPayment();
    void process();

    String getReferenceNumber();

    void setReferenceNumber(String referenceNumber);
}
