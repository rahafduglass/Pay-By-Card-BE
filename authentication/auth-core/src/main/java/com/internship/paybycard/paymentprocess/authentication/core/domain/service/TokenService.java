package com.internship.paybycard.paymentprocess.authentication.core.domain.service;

import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.TokenResponse;
import com.internship.paybycard.paymentprocess.authentication.core.domain.result.Result;

public interface TokenService {
    Result<TokenResponse> generateToken(String username);
    Boolean validateToken(String token);
    String extractUsername(String jwt);
}
