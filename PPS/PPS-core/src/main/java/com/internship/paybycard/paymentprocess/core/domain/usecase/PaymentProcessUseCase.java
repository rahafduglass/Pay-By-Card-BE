package com.internship.paybycard.paymentprocess.core.domain.usecase;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.CompletePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.VerifyPaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.response.InitiatePaymentUseCaseResponse;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;

public interface PaymentProcessUseCase {
    Result<InitiatePaymentUseCaseResponse> initiatePayment(InitiatePaymentCommand command);

    Result<Void> verifyPayment(VerifyPaymentCommand command);

    Result<Void> completePayment(CompletePaymentCommand command);
}
