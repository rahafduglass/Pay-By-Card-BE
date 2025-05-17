package com.internship.paybycard.paymentprocess.terminalui.handler;

import com.internship.paybycard.paymentprocess.core.domain.dto.card.VerifyCardDtoImpl;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.core.domain.result.Status;
import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import com.internship.paybycard.paymentprocess.terminalui.dto.payment.command.CompletePaymentCommandImpl;
import com.internship.paybycard.paymentprocess.terminalui.util.ConsoleHandler;
import com.internship.paybycard.paymentprocess.terminalui.validation.completepayment.OtpValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CompletePaymentInputHandler {
    private final ConsoleHandler consoleHandler;
    private final PaymentProcessUseCase paymentProcessUseCase;

    public void completePayment(String referenceNumber, VerifyCardDtoImpl card) {
        CompletePaymentCommandImpl command = new CompletePaymentCommandImpl();
        command.setOTP(consoleHandler.requestFieldInput("please enter the OTP sent to your email:", "OTP", new OtpValidator()));
        command.setPaymentReference(referenceNumber);
        command.setVerifyCard(card);

        Result<Void> response= paymentProcessUseCase.completePayment(command);
        generateResponse(response);
    }

    private void generateResponse(Result<Void> response) {
        if (response.getStatus() == Status.ACT) {
            consoleHandler.positiveFeedbackMessage("payment completed successfully", " THANK YOU :D ");
        } else {
            consoleHandler.negativeFeedbackMessage(response.getErrorCode().name(), " | try again: ");
        }
    }

}
