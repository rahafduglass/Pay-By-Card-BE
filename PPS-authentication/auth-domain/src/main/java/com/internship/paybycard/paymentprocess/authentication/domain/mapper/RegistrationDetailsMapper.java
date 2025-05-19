package com.internship.paybycard.paymentprocess.authentication.domain.mapper;

import com.internship.paybycard.paymentprocess.authentication.core.presentation.command.RegistrationCommand;
import com.internship.paybycard.paymentprocess.authentication.domain.dto.RegistrationDetailsImpl;

public class RegistrationDetailsMapper {
    public RegistrationDetailsImpl toRegistrationDetails(RegistrationCommand command) {
        RegistrationDetailsImpl registrationDetails = new RegistrationDetailsImpl();
        registrationDetails.setUsername(command.getUsername());
        registrationDetails.setEmail(command.getEmail());
        return registrationDetails;
    }
}
