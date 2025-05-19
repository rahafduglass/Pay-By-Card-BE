package com.internship.paybycard.paymentprocess.terminalui.validation.initiatepayment;

import com.internship.paybycard.paymentprocess.terminalui.validation.Validator;

public class CvvValidator implements Validator {


    @Override
    public boolean validate(String cvv) {
        if (cvv.length() != 3) {
            System.out.println(RED + "cvv must be 3 characters" +RESET);
            return false;
        }
        return true;
    }
}
