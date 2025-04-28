package com.internship.paybycard.paymentprocess.core.infrastructure;

import com.internship.paybycard.paymentprocess.core.domain.model.PaymentModel;

import java.util.Optional;

public interface PaymentDao {

    PaymentModel createPayment(PaymentModel model);
    PaymentModel findPaymentByReferenceNumber(String referenceNumber);
    void updatePaymentConfirmedByReferenceNumber(String referenceNumber, Boolean confirmed);

}
