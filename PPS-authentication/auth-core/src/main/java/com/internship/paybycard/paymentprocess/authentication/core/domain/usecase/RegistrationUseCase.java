package com.internship.paybycard.paymentprocess.authentication.core.domain.usecase;

import com.internship.paybycard.paymentprocess.authentication.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.authentication.core.presentation.command.RegistrationCommand;

public interface RegistrationUseCase {
    Result<Void> register(RegistrationCommand command);
}
