package com.internship.paybycard.validation;

@FunctionalInterface
public interface Validator {
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";

    boolean validate(String field);
}
