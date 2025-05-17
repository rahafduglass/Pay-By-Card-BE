package com.internship.paybycard.paymentprocess.terminalui.handler;

import com.internship.paybycard.paymentprocess.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.core.domain.result.Status;
import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import com.internship.paybycard.paymentprocess.terminalui.dto.payment.command.VerifyPaymentCommandImpl;
import com.internship.paybycard.paymentprocess.terminalui.util.ConsoleHandler;
import com.internship.paybycard.paymentprocess.terminalui.validation.verifypayment.ReferenceNumberValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VerifyPaymentInputHandler {
    private final ConsoleHandler consoleHandler;
    private final PaymentProcessUseCase paymentProcessUseCase;

    public void verifyPayment(String paymentReference) {
        if(!new ReferenceNumberValidator().validate(paymentReference)) {
            generateResponse(new Result<>(Status.RJC, null, null));
            return;
        }
        generateResponse(paymentProcessUseCase.verifyPayment(requestAndBuildCommand(paymentReference)));
    }

    private void generateResponse(Result<Void> response) {
        if (response.getStatus() == Status.ACT)
            consoleHandler.positiveFeedbackMessage("OTP sent to your email successfully", " | check your email to complete payment..");
        else
            consoleHandler.negativeFeedbackMessage("failed to verify payment", " | try again, consider initiating payment first ");
    }

    private VerifyPaymentCommandImpl requestAndBuildCommand(String paymentReference) {
        VerifyPaymentCommandImpl command = new VerifyPaymentCommandImpl();
        command.setPaymentReference(paymentReference);
        return command;
    }

}
