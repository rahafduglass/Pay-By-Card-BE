package com.internship.paybycard.paymentprocess.authentication.domain.dto;

import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.TokenResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtTokenResponseImpl implements TokenResponse {
    private String token;
}
