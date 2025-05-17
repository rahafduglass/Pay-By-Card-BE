package com.internship.paybycard.paymentprocess.terminalui.handler;

import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import com.internship.paybycard.paymentprocess.terminalui.util.ConsoleHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentInputHandler {
    private final ConsoleHandler consoleHandler;
    private final PaymentProcessUseCase paymentProcessUseCase;

    public String initiatePayment() {
        InitiatePaymentInputHandler handler = new InitiatePaymentInputHandler(consoleHandler, paymentProcessUseCase);
        return handler.initiatePayment();
    }
}
