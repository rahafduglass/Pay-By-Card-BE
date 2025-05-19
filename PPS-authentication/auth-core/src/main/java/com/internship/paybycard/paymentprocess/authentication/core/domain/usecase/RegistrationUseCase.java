package com.internship.paybycard.paymentprocess.authentication.core.domain.usecase;

import com.internship.paybycard.paymentprocess.authentication.core.presentation.command.RegistrationCommand;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;

public interface RegistrationUseCase {
    Result<Void> register(RegistrationCommand command);
}
