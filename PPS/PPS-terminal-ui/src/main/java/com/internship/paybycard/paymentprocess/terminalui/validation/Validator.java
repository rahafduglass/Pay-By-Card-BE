package com.internship.paybycard.paymentprocess.terminalui.validation;

@FunctionalInterface
public interface Validator {
    String RED = "\u001B[31m";
    String RESET = "\u001B[0m";
    String GREEN = "\u001B[32m";
    String ORANGE = "\u001B[38;5;208m";
    String BLUE = "\u001B[34m";

    boolean validate(String field);
}
