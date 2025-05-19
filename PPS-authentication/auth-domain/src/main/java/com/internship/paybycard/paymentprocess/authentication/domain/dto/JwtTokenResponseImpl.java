package com.internship.paybycard.paymentprocess.authentication.domain.dto;

import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.JwtTokenResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtTokenResponseImpl implements JwtTokenResponse {
    private String token;
}
