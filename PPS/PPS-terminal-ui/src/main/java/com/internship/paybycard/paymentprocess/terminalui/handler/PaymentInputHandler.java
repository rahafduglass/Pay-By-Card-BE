package com.internship.paybycard.paymentprocess.terminalui.handler;

import com.internship.paybycard.paymentprocess.core.domain.dto.card.VerifyCardDtoImpl;
import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import com.internship.paybycard.paymentprocess.terminalui.dto.response.InitiatePaymentHandlerResponse;
import com.internship.paybycard.paymentprocess.terminalui.util.ConsoleHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentInputHandler {
    private final ConsoleHandler consoleHandler;
    private final PaymentProcessUseCase paymentProcessUseCase;

    public InitiatePaymentHandlerResponse initiatePayment() {
        return new InitiatePaymentInputHandler(paymentProcessUseCase, consoleHandler).initiatePayment();
    }

    public void verifyPayment(String paymentReferenceNumber) {
        new VerifyPaymentInputHandler(consoleHandler, paymentProcessUseCase).verifyPayment(paymentReferenceNumber);
    }

    public void completePayment(String paymentReferenceNumber, VerifyCardDtoImpl card) {
        new CompletePaymentInputHandler(consoleHandler, paymentProcessUseCase).completePayment(paymentReferenceNumber, card);
    }

}
