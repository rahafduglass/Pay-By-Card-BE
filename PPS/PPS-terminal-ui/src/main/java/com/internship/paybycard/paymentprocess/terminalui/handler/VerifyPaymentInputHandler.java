package com.internship.paybycard.paymentprocess.terminalui.handler;

import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import com.internship.paybycard.paymentprocess.terminalui.dto.payment.command.VerifyPaymentCommandImpl;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class VerifyPaymentInputHandler {
    private final Scanner scanner;
    private final PaymentProcessUseCase paymentProcessUseCase;

    public void verifyPayment() {
        VerifyPaymentCommandImpl command = new VerifyPaymentCommandImpl();
        System.out.println("Please enter payment reference: ");
        command.setPaymentReference(scanner.nextLine());
    }
}
