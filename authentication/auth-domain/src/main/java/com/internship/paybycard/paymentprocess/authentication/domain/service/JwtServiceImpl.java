package com.internship.paybycard.paymentprocess.authentication.domain.service;

import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.JwtTokenResponse;
import com.internship.paybycard.paymentprocess.authentication.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.authentication.core.domain.result.Status;
import com.internship.paybycard.paymentprocess.authentication.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.authentication.core.domain.service.JwtService;
import com.internship.paybycard.paymentprocess.authentication.domain.dto.JwtTokenResponseImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
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
    public Boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody();
    }
}