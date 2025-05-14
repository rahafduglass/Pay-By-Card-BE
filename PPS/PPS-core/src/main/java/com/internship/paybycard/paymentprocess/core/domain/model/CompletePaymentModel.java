package com.internship.paybycard.paymentprocess.core.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.result.Result;

public interface CompletePaymentModel {
    void verifyOTP();

    void pay();

    Result<Void> result();
}
