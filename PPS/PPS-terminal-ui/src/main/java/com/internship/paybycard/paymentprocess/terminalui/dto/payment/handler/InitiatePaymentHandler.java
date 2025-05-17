package com.internship.paybycard.paymentprocess.terminalui.dto.payment.handler;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.response.InitiatePaymentUseCaseResponse;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.core.domain.result.Status;
import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import com.internship.paybycard.paymentprocess.terminalui.dto.card.VerifyCardDetailsImpl;
import com.internship.paybycard.paymentprocess.terminalui.dto.payment.command.InitiatePaymentCommandImpl;
import com.internship.paybycard.paymentprocess.terminalui.validation.Validator;
import com.internship.paybycard.paymentprocess.terminalui.validation.initiatepayment.*;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import static com.internship.paybycard.paymentprocess.terminalui.validation.Validator.*;

@RequiredArgsConstructor
public class InitiatePaymentHandler {
    private final Scanner scanner;
    private final PaymentProcessUseCase paymentProcessUseCase;

    public String initiatePayment() {
        InitiatePaymentCommandImpl command = new InitiatePaymentCommandImpl();
        VerifyCardDetailsImpl card = new VerifyCardDetailsImpl();

        command.setItems(setField("enter items: ", "items"));
        command.setAmount(new BigDecimal(setField("enter amount:", "amount", new AmountValidator())));
        command.setClientName(setField("enter client name: ", "client name", new ClientNameValidator()));

        card.setCardNumber(setField("enter card number: ", "card number", new CardNumberValidator()));
        card.setCVV(setField("enter cvv: ", "cvv", new CvvValidator()));
        card.setExpiryDate(LocalDate.parse(setField("enter expiry date: ", "expiry date", new ExpiryDateValidator())));
        command.setCard(card);

        Result<InitiatePaymentUseCaseResponse> response = paymentProcessUseCase.initiatePayment(command);
        return generateResponse(response);
    }

    private String generateResponse(Result<InitiatePaymentUseCaseResponse> response) {
        if (response.getStatus() == Status.ACT) {
            System.out.println(GREEN + response.getData().getMessage() +ORANGE+" * proceed with verify payment * "+ RESET);
            return response.getData().getReferenceNumber();
        } else {
            System.out.println(RED + response.getErrorCode() +ORANGE+ " => try again: " + RESET);
            return null;
        }
    }

    private String setField(String message, String fieldName, Validator validator) {
        String field;
        try {
            System.out.println(message);
            do {
                field = scanner.nextLine();
            } while (!validator.validate(field));
            return field;
        } catch (Exception e) {
            return setField(RED + "please enter a valid " + fieldName + " format" + RESET, fieldName, validator);
        }
    }

    private String setField(String message, String fieldName) {
        String field;
        try {
            System.out.println(message);
            field = scanner.nextLine();
            return field;
        } catch (Exception e) {
            return setField(RED + "please enter a valid " + fieldName + " format" + RESET, fieldName);
        }
    }


}
