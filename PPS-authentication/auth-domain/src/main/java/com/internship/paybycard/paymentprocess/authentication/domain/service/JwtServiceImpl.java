package com.internship.paybycard.paymentprocess.authentication.domain.service;

import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.JwtTokenResponse;
import com.internship.paybycard.paymentprocess.authentication.core.domain.service.JwtService;
import com.internship.paybycard.paymentprocess.authentication.domain.dto.JwtTokenResponseImpl;
import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.core.domain.result.Status;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret-key}")
    @Setter
    @Getter
    private String secretKey;

    @Override
    public Result<JwtTokenResponse> generateToken(String username) {
        Date oneDayLater = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
        String jwt = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(oneDayLater)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(getSecretKey())))
                .compact();
        return new Result<>(Status.ACT, ErrorCode.SUCCESS, new JwtTokenResponseImpl(jwt));
    }

    @Override
    public Result<Void> validateToken(String token) {
        return null;
    }
}
