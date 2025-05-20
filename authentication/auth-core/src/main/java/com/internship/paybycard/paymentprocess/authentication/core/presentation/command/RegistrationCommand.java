package com.internship.paybycard.paymentprocess.authentication.core.presentation.command;


public interface RegistrationCommand {
    public String getUsername();
    public String getPassword();
    public String getEmail();
}
