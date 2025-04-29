package com.internship.paybycard.paymentprocess.core.domain.command;

public interface CompletePaymentCommand {
    String getPaymentReference();
    String getOTP();

    void setPaymentReference(String paymentReference);
    void setOTP(String otp);
}
