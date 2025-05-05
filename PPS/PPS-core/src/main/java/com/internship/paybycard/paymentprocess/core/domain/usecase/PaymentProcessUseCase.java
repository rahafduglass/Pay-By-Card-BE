package com.internship.paybycard.paymentprocess.core.domain.usecase;

import com.internship.paybycard.paymentprocess.core.domain.dto.command.payment.CompletePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.command.payment.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.command.payment.RequestPaymentVerificationCommand;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;

public interface PaymentProcessUseCase {

    <T> Result<T> initiatePayment(InitiatePaymentCommand command);
    <T> Result<T> requestPaymentVerification(RequestPaymentVerificationCommand command);
    <T> Result<T> completePayment(CompletePaymentCommand command);

}
