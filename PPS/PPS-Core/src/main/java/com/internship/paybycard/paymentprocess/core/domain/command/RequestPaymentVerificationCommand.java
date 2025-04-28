package com.internship.paybycard.paymentprocess.core.domain.command;

public interface RequestPaymentVerificationCommand {

    String getPaymentReference();

    void setPaymentReference(String paymentReference);
}
