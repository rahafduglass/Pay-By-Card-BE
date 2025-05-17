package com.internship.paybycard.paymentprocess.terminalui.dto.payment.handler;

import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;

import java.util.Scanner;

public class PaymentHandler {
    private final Scanner scanner;
    private final PaymentProcessUseCase paymentProcessUseCase;

    public PaymentHandler(Scanner scanner, PaymentProcessUseCase paymentProcessUseCase) {
        this.scanner = scanner;
        this.paymentProcessUseCase = paymentProcessUseCase;
    }

    public String initiatePayment(){
        InitiatePaymentHandler handler = new InitiatePaymentHandler(scanner, paymentProcessUseCase);
        return handler.initiatePayment();
    }


}
