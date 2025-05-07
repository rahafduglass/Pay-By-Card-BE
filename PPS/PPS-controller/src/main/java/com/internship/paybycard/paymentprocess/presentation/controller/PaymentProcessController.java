package com.internship.paybycard.paymentprocess.presentation.controller;

import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import com.internship.paybycard.paymentprocess.presentation.dto.request.payment.CompletePaymentCommandImpl;
import com.internship.paybycard.paymentprocess.presentation.dto.request.payment.InitiatePaymentCommandImpl;
import com.internship.paybycard.paymentprocess.presentation.dto.request.payment.VerifyPaymentCommandImpl;
import com.internship.paybycard.paymentprocess.presentation.dto.response.InitiatePaymentResponse;
import com.internship.paybycard.paymentprocess.presentation.formatter.PaymentFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/paymentProcess")
@RequiredArgsConstructor
public class PaymentProcessController {

    private final PaymentProcessUseCase paymentProcessUseCase;
    private final PaymentFormatter paymentFormatter;

    @PostMapping("/initiate")
    public ResponseEntity<InitiatePaymentResponse> initiatePayment(@RequestBody InitiatePaymentCommandImpl command) {
        Result<String> result = paymentProcessUseCase.initiatePayment(command);
        if (result.getStatus().name().equals("RJC")) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentFormatter.toInitiatePaymentResponse(result.getData()));
    }

    @PutMapping("/verify")
    public ResponseEntity<Void> verifyPayment(@RequestBody VerifyPaymentCommandImpl command) {
        Result<Void> result = paymentProcessUseCase.verifyPayment(command);
        if (result.getStatus().name().equals("RJC")) {
            return rejectResponse(result.getErrorCode());
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/complete")
    public ResponseEntity<Void> completePayment(@RequestBody CompletePaymentCommandImpl command) {
        Result<Void> result = paymentProcessUseCase.completePayment(command);
        if (result.getStatus().name().equals("RJC")) {
            return rejectResponse(result.getErrorCode());
        }
        return ResponseEntity.noContent().build();
    }

    private <T> ResponseEntity<T> rejectResponse(ErrorCode errorCode) {
        return switch (errorCode) {
            case INVALID_COMMAND_INPUT, INVALID_PAYMENT_INPUT, INVALID_CARD, EMPTY_REFERENCE_NUMBER,
                 PAYMENT_NOT_FOUND -> ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        };
    }

}
