package com.internship.paybycard.validation.initiatepayment;

import com.internship.paybycard.validation.Validator;

public class CardNumberValidator implements Validator {

    @Override
    public boolean validate(String cardNumber) {
        if (cardNumber.length() != 36) {
            System.out.println(RED + "card number must be 36 characters" + RESET);
            return false;
        }
        return true;
    }
}
