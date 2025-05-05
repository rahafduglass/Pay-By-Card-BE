package com.internship.paybycard.paymentprocess.core.persistence;

import com.internship.paybycard.paymentprocess.core.domain.dto.PaymentDto;

public interface PaymentDao {

    PaymentDto createPayment(PaymentDto model);
    PaymentDto findPaymentByReferenceNumber(String referenceNumber);
    void updatePaymentConfirmedByReferenceNumber(String referenceNumber, Boolean confirmed);

}
