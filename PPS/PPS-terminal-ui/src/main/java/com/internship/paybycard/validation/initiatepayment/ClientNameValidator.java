package com.internship.paybycard.validation.initiatepayment;

import com.internship.paybycard.validation.Validator;

public class ClientNameValidator implements Validator {

    @Override
    public boolean validate(String clientName) {
        if (clientName.length() < 2) {
            System.out.println(RED + "client name must be at least 2 characters" + RESET);
            return false;
        }
        return true;
    }
}
