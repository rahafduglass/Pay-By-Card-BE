package com.internship.paybycard.paymentprocess.terminalui.validation.verifypayment;

import com.internship.paybycard.paymentprocess.terminalui.validation.Validator;

public class ReferenceNumberValidator implements Validator {
    @Override
    public boolean validate(String referenceNumber) {
        return referenceNumber != null && referenceNumber.length() == 36;
    }
}
