package com.internship.paybycard.paymentprocess.core.domain.usecase;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.CompletePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.VerifyPaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;

public interface PaymentProcessUseCase {

    <T> Result<T> initiatePayment(InitiatePaymentCommand command);
    <T> Result<T> verifyPayment(VerifyPaymentCommand command);
    <T> Result<T> completePayment(CompletePaymentCommand command);

}
