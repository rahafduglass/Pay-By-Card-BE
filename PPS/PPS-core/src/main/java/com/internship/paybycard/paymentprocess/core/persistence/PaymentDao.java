package com.internship.paybycard.paymentprocess.core.persistence;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.RealPaymentDto;

public interface PaymentDao {
    PaymentDto createPayment(RealPaymentDto model);

    PaymentDto findPaymentByReferenceNumber(String referenceNumber);

    int updatePaymentConfirmedByReferenceNumber(String referenceNumber, Boolean confirmed);
}
