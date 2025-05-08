package com.internship.paybycard.paymentprocess.core.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.PaymentDto;

public interface InitiatePaymentModel {
    PaymentDto process() throws IllegalAccessException;

    boolean validatePayment();
}
