package com.internship.paybycard.paymentprocess.terminalui.validation.completepayment;

import com.internship.paybycard.paymentprocess.terminalui.validation.Validator;

public class OtpValidator implements Validator {

    @Override
    public boolean validate(String otp) {
        if (otp == null || otp.length() != 4) {
            System.out.println(RED + "amount must be between 0 and 10000" + RESET);
            return false;
        }
        return true;
    }
}
