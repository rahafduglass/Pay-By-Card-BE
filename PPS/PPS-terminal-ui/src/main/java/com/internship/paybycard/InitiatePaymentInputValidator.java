package com.internship.paybycard;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Deprecated
public class InitiatePaymentInputValidator {
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";



    public boolean validateAmount(String amount) {
        double amountParsed=Double.parseDouble(amount);
        if (amountParsed <= 0 || amountParsed > 10000) {
            System.out.println(RED + "amount must be between 0 and 10000" + RESET);
            return false;
        }
        return true;
    }

    public boolean validateCardNumber(String cardNumber) {
        if (cardNumber.length() != 36) {
            System.out.println(RED + "card number must be 36 characters" + RESET);
            return false;
        }
        return true;
    }

    public boolean validateCvv(String cvv) {
        if (cvv.length() != 3) {
            System.out.println(RED + "cvv must be 3 characters" + RESET);
            return false;
        }
        return true;
    }

    public boolean validateExpirationDate(String expiryDate) {
        try {
            LocalDate parsedDate = LocalDate.parse(expiryDate, DateTimeFormatter.ISO_LOCAL_DATE);
            if (parsedDate.isAfter(LocalDate.now())) {
                System.out.println(RED + "expiry date must be in the future" + RESET);

            }
        } catch (Exception e) {
            System.out.println(RED + "expiry date must be in a Date format" + RESET);
            return false;
        }
        return false;
    }

    public boolean validateClientName(String clientName) {
        if (clientName.length() < 4) {
            System.out.println(RED + "client name must be at least 4 characters" + RESET);
            return false;
        }
        return true;
    }
}
