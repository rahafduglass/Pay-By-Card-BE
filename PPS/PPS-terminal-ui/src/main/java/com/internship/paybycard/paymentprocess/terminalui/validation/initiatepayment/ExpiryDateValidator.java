package com.internship.paybycard.paymentprocess.terminalui.validation.initiatepayment;

import com.internship.paybycard.paymentprocess.terminalui.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExpiryDateValidator implements Validator {
    @Override
    public boolean validate(String expiryDate) {
        try {
            LocalDate parsedDate = LocalDate.parse(expiryDate, DateTimeFormatter.ISO_LOCAL_DATE);
            if (!parsedDate.isAfter(LocalDate.now())) {
                System.out.println(RED + "expiry date must be in the future" + RESET);
            }
            return true;
        } catch (Exception e) {
            System.out.println(RED + "expiry date must be in a Date format" + RESET);
            return false;
        }
    }
}
