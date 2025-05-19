package com.internship.paybycard.paymentprocess.authentication.core.domain.service;

import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.JwtTokenResponse;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;

public interface JwtService {
    Result<JwtTokenResponse> generateToken(String username);
    Result<Void> validateToken(String token);
}
