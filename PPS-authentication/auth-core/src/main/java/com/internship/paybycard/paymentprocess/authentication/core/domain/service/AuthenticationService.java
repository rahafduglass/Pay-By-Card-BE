package com.internship.paybycard.paymentprocess.authentication.core.domain.service;

import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.JwtTokenResponse;
import com.internship.paybycard.paymentprocess.authentication.core.presentation.command.RegistrationCommand;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;

public interface AuthenticationService {
    Result<JwtTokenResponse> login(String username, String password);
    Result<Void> register(RegistrationCommand userDetails);
}
