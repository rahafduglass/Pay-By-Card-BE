package com.internship.paybycard.paymentprocess.authentication.core.domain.usecase;

import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.TokenResponse;
import com.internship.paybycard.paymentprocess.authentication.core.domain.result.Result;

public interface LoginUsecase {
    Result<TokenResponse> login(String username, String password);
}
