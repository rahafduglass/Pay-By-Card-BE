package com.internship.paybycard.paymentprocess.authentication.controller.controller;

import com.internship.paybycard.paymentprocess.authentication.controller.dto.LoginResponse;
import com.internship.paybycard.paymentprocess.authentication.controller.dto.request.LoginRequest;
import com.internship.paybycard.paymentprocess.authentication.controller.dto.request.RegistrationRequest;
import com.internship.paybycard.paymentprocess.authentication.controller.util.AESUtil;
import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.TokenResponse;
import com.internship.paybycard.paymentprocess.authentication.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.authentication.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.authentication.core.domain.result.Status;
import com.internship.paybycard.paymentprocess.authentication.core.domain.usecase.LoginUsecase;
import com.internship.paybycard.paymentprocess.authentication.core.domain.usecase.RegistrationUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final LoginUsecase loginUsecase;
    private final RegistrationUseCase registrationUseCase;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegistrationRequest command) {
        Result<Void> result = registrationUseCase.register(command);
        if (result.getStatus().equals(Status.ACT))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        loginRequest.setPassword(AESUtil.decrypt(loginRequest.getPassword()));
        Result<TokenResponse> result = loginUsecase.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (result.getErrorCode().equals(ErrorCode.SUCCESS)) {
            LoginResponse response = new LoginResponse(result.getData().getToken());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else if (result.getErrorCode().equals(ErrorCode.INVALID_CREDENTIALS)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }
}
