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
        InitiatePaymentInputHandler handler = new InitiatePaymentInputHandler(paymentProcessUseCase,consoleHandler);
        return handler.initiatePayment();
    }
    public void verifyPayment(String paymentReferenceNumber) {
        VerifyPaymentInputHandler handler= new VerifyPaymentInputHandler(consoleHandler,paymentProcessUseCase);
        handler.verifyPayment(paymentReferenceNumber);
    }
    public void completePayment(String paymentReferenceNumber, VerifyCardDtoImpl card) {
        CompletePaymentInputHandler handler = new CompletePaymentInputHandler(consoleHandler,paymentProcessUseCase);
        handler.completePayment(paymentReferenceNumber,card);
    }

}
