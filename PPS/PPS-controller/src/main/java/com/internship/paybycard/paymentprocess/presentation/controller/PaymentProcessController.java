package com.internship.paybycard.paymentprocess.presentation.controller;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.response.InitiatePaymentUseCaseResponse;
import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.core.domain.result.Status;
import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import com.internship.paybycard.paymentprocess.presentation.dto.request.payment.CompletePaymentCommandImpl;
import com.internship.paybycard.paymentprocess.presentation.dto.request.payment.InitiatePaymentCommandImpl;
import com.internship.paybycard.paymentprocess.presentation.dto.request.payment.VerifyPaymentCommandImpl;
import com.internship.paybycard.paymentprocess.presentation.dto.response.InitiatePaymentResponse;
import com.internship.paybycard.paymentprocess.presentation.formatter.PaymentFormatter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
@RequestMapping("api/v1/payment-process")
@RequiredArgsConstructor
public class PaymentProcessController {

    private final PaymentProcessUseCase paymentProcessUseCase;
    private final PaymentFormatter paymentFormatter;

    @PostMapping("/initiate")
    public ResponseEntity<InitiatePaymentResponse> initiatePayment(@RequestBody @Valid InitiatePaymentCommandImpl command) {
        Result<InitiatePaymentUseCaseResponse> result = paymentProcessUseCase.initiatePayment(command);
        return paymentFormatter.toInitiatePaymentResponse(result);
    }

    @PutMapping("/verify")
    public ResponseEntity<Void> verifyPayment(@RequestBody @Valid VerifyPaymentCommandImpl command) {
        Result<Void> result = paymentProcessUseCase.verifyPayment(command);
        if (result.getStatus() == Status.RJC) {
            return rejectResponse(result.getErrorCode());
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/complete")
    public ResponseEntity<Void> completePayment(@RequestBody @Valid CompletePaymentCommandImpl command) {
        Result<Void> result = paymentProcessUseCase.completePayment(command);
        if (result.getStatus() == Status.RJC) {
            return rejectResponse(result.getErrorCode());
        }
        return ResponseEntity.noContent().build();
    }

    private <T> ResponseEntity<T> rejectResponse(ErrorCode errorCode) {
        return switch (errorCode) {
            case INVALID_CARD, PAYMENT_NOT_FOUND, INSUFFICIENT_CARD_BALANCE, INVALID_OR_EXPIRED_OTP ->
                    ResponseEntity.status(UNPROCESSABLE_ENTITY).build();
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        };
    }
}
