package com.internship.paybycard.paymentprocess.authentication.core.domain.mapper;

import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.RegistrationDetails;
import com.internship.paybycard.paymentprocess.authentication.core.presentation.command.RegistrationCommand;

public interface RegistrationDetailsMapper {
    public RegistrationDetails toRegistrationDetails(RegistrationCommand command);
}
