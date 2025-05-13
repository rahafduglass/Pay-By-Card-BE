package com.internship.paybycard.paymentprocess.core.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.response.InitiatePaymentUseCaseResponse;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;

public interface InitiatePaymentModel {
    void process();

    void validatePayment();

    Result<InitiatePaymentUseCaseResponse> result();
}
