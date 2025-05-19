package com.internship.paybycard.paymentprocess.authentication.domain.service;

import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.JwtTokenResponse;
import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.RegistrationDetails;
import com.internship.paybycard.paymentprocess.authentication.core.domain.service.AuthenticationService;
import com.internship.paybycard.paymentprocess.authentication.core.domain.service.JwtService;
import com.internship.paybycard.paymentprocess.authentication.core.domain.usecase.RegistrationUseCase;
import com.internship.paybycard.paymentprocess.authentication.core.persistence.SysUserDao;
import com.internship.paybycard.paymentprocess.authentication.core.presentation.command.RegistrationCommand;
import com.internship.paybycard.paymentprocess.authentication.domain.dto.RegistrationDetailsImpl;
import com.internship.paybycard.paymentprocess.authentication.domain.mapper.RegistrationDetailsMapper;
import com.internship.paybycard.paymentprocess.authentication.domain.usecase.LoginUsecaseImpl;
import com.internship.paybycard.paymentprocess.authentication.domain.usecase.RegistrationUseCaseImpl;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final SysUserDao sysUserDao;
    private final RegistrationDetailsMapper registrationDetailsMapper;
    @Override
    public Result<JwtTokenResponse> login(String username, String password) {
        log.info("login user, with username: {}", username);
        LoginUsecaseImpl loginUseCase = new LoginUsecaseImpl(authenticationManager, jwtService);
        return loginUseCase.login(username, password);
    }

    @Override
    public Result<Void> register(RegistrationCommand command) {
        log.info("registering user, username: {}", command.getUsername());
        RegistrationUseCaseImpl registrationUseCase= new RegistrationUseCaseImpl(passwordEncoder,sysUserDao,registrationDetailsMapper);
        return registrationUseCase.register(command);
    }
}