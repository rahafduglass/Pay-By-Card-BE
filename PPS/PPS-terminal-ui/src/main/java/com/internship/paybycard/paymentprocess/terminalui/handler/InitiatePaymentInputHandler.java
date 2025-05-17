package com.internship.paybycard.paymentprocess.terminalui.handler;

import com.internship.paybycard.paymentprocess.core.domain.dto.card.VerifyCardDtoImpl;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.response.InitiatePaymentUseCaseResponse;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.core.domain.result.Status;
import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import com.internship.paybycard.paymentprocess.terminalui.dto.card.VerifyCardDetailsImpl;
import com.internship.paybycard.paymentprocess.terminalui.dto.payment.command.InitiatePaymentCommandImpl;
import com.internship.paybycard.paymentprocess.terminalui.dto.response.InitiatePaymentHandlerResponse;
import com.internship.paybycard.paymentprocess.terminalui.util.ConsoleHandler;
import com.internship.paybycard.paymentprocess.terminalui.validation.initiatepayment.*;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
public class InitiatePaymentInputHandler {
    private final PaymentProcessUseCase paymentProcessUseCase;
    private final ConsoleHandler consoleHandler;

    public InitiatePaymentHandlerResponse initiatePayment() {
        InitiatePaymentCommandImpl command= requestAndBuildCommand();
       return generateResponse(paymentProcessUseCase.initiatePayment(command),command);
    }

    private InitiatePaymentHandlerResponse generateResponse(Result<InitiatePaymentUseCaseResponse> useCaseResponse,InitiatePaymentCommandImpl command) {
        if (useCaseResponse.getStatus() == Status.ACT) {
            consoleHandler.positiveFeedbackMessage(useCaseResponse.getData().getMessage(), " | proceed with verify payment * ");
            InitiatePaymentHandlerResponse handlerResponse = new InitiatePaymentHandlerResponse();
            handlerResponse.setPaymentReferenceNumber(useCaseResponse.getData().getReferenceNumber());
            VerifyCardDtoImpl card= new VerifyCardDtoImpl();
            card.setCardNumber(command.getCard().getCardNumber());
            card.setExpiryDate(command.getCard().getExpiryDate());
            card.setCVV(command.getCard().getCVV());
            handlerResponse.setCard(card);
            return handlerResponse;
        } else {
            consoleHandler.negativeFeedbackMessage(useCaseResponse.getErrorCode().name(), " | try again: ");
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