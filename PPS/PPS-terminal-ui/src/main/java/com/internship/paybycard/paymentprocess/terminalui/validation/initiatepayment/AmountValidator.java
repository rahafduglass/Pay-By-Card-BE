package com.internship.paybycard.paymentprocess.terminalui.validation.initiatepayment;

import com.internship.paybycard.paymentprocess.terminalui.validation.Validator;

public class AmountValidator implements Validator {

    @Override
    public boolean validate(String amount) {
        double amountParsed=Double.parseDouble(amount);
        if (amountParsed <= 0 || amountParsed > 10000) {
            System.out.println(RED + "amount must be between 0 and 10000" + RESET);
            return false;
        }
        return true;    }
}
