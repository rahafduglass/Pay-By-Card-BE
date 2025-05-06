package com.internship.paybycard.paymentprocess.core.persistence;

import com.internship.paybycard.paymentprocess.core.domain.dto.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.dto.RealPaymentDto;

public interface PaymentDao {

    PaymentDto createPayment(RealPaymentDto model);
    PaymentDto findPaymentByReferenceNumber(String referenceNumber);
    void updatePaymentConfirmedByReferenceNumber(String referenceNumber, Boolean confirmed);

}
