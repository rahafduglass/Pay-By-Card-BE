package com.internship.paybycard.paymentprocess.core.domain.usecase;

import com.internship.paybycard.paymentprocess.core.domain.command.CompletePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.command.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.command.RequestPaymentVerificationCommand;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;

public interface PaymentProcessUseCase {

    Result initiatePayment(InitiatePaymentCommand command);
    Result requestPaymentVerification(RequestPaymentVerificationCommand command);
    Result completePayment(CompletePaymentCommand command);

}
