package com.internship.paybycard.paymentprocess.authentication.core.domain.usecase;

import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.JwtTokenResponse;
import com.internship.paybycard.paymentprocess.authentication.core.domain.result.Result;

public interface LoginUsecase {
    Result<JwtTokenResponse> login(String username, String password);
}
