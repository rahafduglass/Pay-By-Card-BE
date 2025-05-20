package com.internship.paybycard.paymentprocess.authentication.controller.dto.request;

import com.internship.paybycard.paymentprocess.authentication.core.presentation.command.RegistrationCommand;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistrationRequest implements RegistrationCommand {
    @NotNull
    @NotEmpty
    private String username;
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    private String email;
}
