package com.internship.paybycard.paymentprocess.authentication.domain.usecase;

import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.TokenResponse;
import com.internship.paybycard.paymentprocess.authentication.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.authentication.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.authentication.core.domain.result.Status;
import com.internship.paybycard.paymentprocess.authentication.core.domain.service.TokenService;
import com.internship.paybycard.paymentprocess.authentication.core.domain.usecase.LoginUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginUsecaseImpl implements LoginUsecase {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Override
    public Result<TokenResponse> login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return tokenService.generateToken(username);
        } catch (AuthenticationException e) {
            log.error("Invalid credentials: {}", e.getMessage());
            return new Result<>(Status.RJC, ErrorCode.INVALID_CREDENTIALS, null);
        } catch (Exception e) {
            log.error("unexpected error: {}", e.getMessage());
            return new Result<>(Status.RJC, ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }
}
