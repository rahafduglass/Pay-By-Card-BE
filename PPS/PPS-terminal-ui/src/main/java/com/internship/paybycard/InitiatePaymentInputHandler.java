package com.internship.paybycard;

import com.internship.paybycard.validation.*;
import com.internship.paybycard.validation.initiatepayment.*;

import java.util.Scanner;

import static com.internship.paybycard.validation.Validator.RED;
import static com.internship.paybycard.validation.Validator.RESET;

public class InitiatePaymentInputHandler {

    private Scanner scanner;

    public InitiatePaymentInputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public String initiatePayment() {

        setField("enter items: ","items");
        setField("enter amount:","amount", new AmountValidator());
        setField("enter client name: ","client name", new ClientNameValidator());
        setField("enter card number: ","card number", new CardNumberValidator());
        setField("enter cvv: ","cvv", new CvvValidator());
        setField("enter expiry date: ","expiry date", new ExpiryDateValidator());

        return null;
        //call initiate payment use case and store reference number locally
    }

    private void setField(String message, String fieldName,Validator validator) {
        String field;
        try {
            System.out.println(message);
            do {
                field = scanner.nextLine();
            } while (!validator.validate(field));
        } catch (Exception e) {
            setField(RED+"please enter a valid " +fieldName+" format"+RESET,fieldName, validator);
        }
    }

    private void setField(String message, String fieldName) {
        String field;
        try {
            System.out.println(message);
            field = scanner.nextLine();
        } catch (Exception e) {
            setField(RED+"please enter a valid " +fieldName+" format"+RESET,fieldName);
        }
    }

}
