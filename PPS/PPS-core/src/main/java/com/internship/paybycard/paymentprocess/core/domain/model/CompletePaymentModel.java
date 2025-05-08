package com.internship.paybycard.paymentprocess.core.domain.model;

public interface CompletePaymentModel {
    void verifyOTP();

    void pay();
}
