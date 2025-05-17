package com.internship.paybycard.paymentprocess.terminalui.util;

import com.internship.paybycard.paymentprocess.terminalui.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

import static com.internship.paybycard.paymentprocess.terminalui.validation.Validator.*;
import static com.internship.paybycard.paymentprocess.terminalui.validation.Validator.RESET;

@RequiredArgsConstructor
public class ConsoleHandler {

    private final Scanner scanner;

    public String requestFieldInput(String requestMessage, String fieldName, Validator fieldValidator) {
        String field;
        try {
            System.out.println(ORANGE+requestMessage+RESET);
            do {
                field = scanner.nextLine();
            } while (!fieldValidator.validate(field));
            return field;
        } catch (Exception e) {
            return requestFieldInput(RED + "please enter a valid " + fieldName + " format" + RESET, fieldName, fieldValidator);
        }
    }

    public String requestFieldInput(String requestMessage, String fieldName) {
        String field;
        try {
            System.out.println(ORANGE+requestMessage+RESET);
            field = scanner.nextLine();
            return field;
        } catch (Exception e) {
            return requestFieldInput(RED + "please enter a valid " + fieldName + " format" + RESET, fieldName);
        }
    }
}
