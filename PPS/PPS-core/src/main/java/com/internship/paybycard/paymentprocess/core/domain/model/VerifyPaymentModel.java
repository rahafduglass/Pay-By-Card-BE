package com.internship.paybycard.paymentprocess.core.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.result.Result;

public interface VerifyPaymentModel {
    void verifyPayment();
    void sendOtp();
    Result<Void> result();
    String getReferenceNumber();
}
