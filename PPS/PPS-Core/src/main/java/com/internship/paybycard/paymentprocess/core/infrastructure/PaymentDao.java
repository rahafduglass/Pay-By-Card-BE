package com.internship.paybycard.paymentprocess.core.infrastructure;

import com.internship.paybycard.paymentprocess.core.domain.model.InitiatePaymentModel;
import com.internship.paybycard.paymentprocess.core.domain.model.PaymentModel;

public interface PaymentDao {

    PaymentModel createPayment(InitiatePaymentModel model);
    PaymentModel findPaymentByReferenceNumber(String referenceNumber);
    PaymentModel updatePaymentConfirmedByReferenceNumber(String referenceNumber, Boolean confirmed);

}
