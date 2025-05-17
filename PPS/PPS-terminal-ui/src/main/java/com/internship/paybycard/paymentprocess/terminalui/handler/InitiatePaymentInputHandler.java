package com.internship.paybycard.paymentprocess.terminalui.handler;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.response.InitiatePaymentUseCaseResponse;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.core.domain.result.Status;
import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import com.internship.paybycard.paymentprocess.terminalui.dto.card.VerifyCardDetailsImpl;
import com.internship.paybycard.paymentprocess.terminalui.dto.payment.command.InitiatePaymentCommandImpl;
import com.internship.paybycard.paymentprocess.terminalui.util.ConsoleHandler;
import com.internship.paybycard.paymentprocess.terminalui.validation.initiatepayment.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.internship.paybycard.paymentprocess.terminalui.validation.Validator.*;


public class InitiatePaymentInputHandler {
    private final PaymentProcessUseCase paymentProcessUseCase;
    private final ConsoleHandler consoleHandler;

    public InitiatePaymentInputHandler(ConsoleHandler consoleHandler, PaymentProcessUseCase paymentProcessUseCase) {
        this.paymentProcessUseCase = paymentProcessUseCase;
        this.consoleHandler = consoleHandler;
    }

    public String initiatePayment() {
        return generateResponse(paymentProcessUseCase.initiatePayment(requestAndBuildCommand()));
    }

    private String generateResponse(Result<InitiatePaymentUseCaseResponse> response) {
        if (response.getStatus() == Status.ACT) {
            System.out.println(GREEN + response.getData().getMessage() + ORANGE + " * proceed with verify payment * " + RESET);
            return response.getData().getReferenceNumber();
        } else {
            System.out.println(RED + response.getErrorCode() + ORANGE + " => try again: " + RESET);
            return null;
        }
    }

    private InitiatePaymentCommandImpl requestAndBuildCommand() {
        InitiatePaymentCommandImpl command = new InitiatePaymentCommandImpl();
        VerifyCardDetailsImpl card = new VerifyCardDetailsImpl();
        command.setItems(consoleHandler.requestFieldInput("enter items: ", "items"));
        command.setAmount(new BigDecimal(consoleHandler.requestFieldInput("enter amount:", "amount", new AmountValidator())));
        command.setClientName(consoleHandler.requestFieldInput("enter client name: ", "client name", new ClientNameValidator()));
        card.setCardNumber(consoleHandler.requestFieldInput("enter card number: ", "card number", new CardNumberValidator()));
        card.setCVV(consoleHandler.requestFieldInput("enter cvv: ", "cvv", new CvvValidator()));
        card.setExpiryDate(LocalDate.parse(consoleHandler.requestFieldInput("enter expiry date: ", "expiry date", new ExpiryDateValidator())));
        command.setCard(card);
        return command;
    }
}