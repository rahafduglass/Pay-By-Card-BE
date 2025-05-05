package com.internship.paybycard.paymentprocess.presentation.controller;

import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.core.domain.result.Status;
import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import com.internship.paybycard.paymentprocess.presentation.controller.dto.command.payment.InitiatePaymentCommandImpl;
import com.internship.paybycard.paymentprocess.presentation.controller.dto.command.payment.VerifyPaymentCommandImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/paymentProcess")
@RequiredArgsConstructor
public class PaymentProcessController {

    private final PaymentProcessUseCase paymentProcessUseCase;

    @PostMapping("/initiate")
    public ResponseEntity<Result<String>> initiatePayment(@RequestBody InitiatePaymentCommandImpl command) {
        Result<String> result = paymentProcessUseCase.initiatePayment(command);
        if (result.getStatus().name().equals("RJC")) {
            return rejectResponse(result.getErrorCode());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/verify")
    public ResponseEntity<Result<Void>> verifyPayment(@RequestBody VerifyPaymentCommandImpl command) {
        Result<Void> result = paymentProcessUseCase.verifyPayment(command);
        if (result.getStatus().name().equals("RJC")) {
            return rejectResponse(result.getErrorCode());
        }
        return ResponseEntity.noContent().build();
    }

    private <T> ResponseEntity<Result<T>> rejectResponse(ErrorCode errorCode) {
        return switch (errorCode) {
            case INVALID_COMMAND_INPUT, INVALID_PAYMENT_INPUT, INVALID_CARD, EMPTY_REFERENCE_NUMBER,
                 PAYMENT_NOT_FOUND ->
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Result<>(Status.RJC, errorCode, null));
            default ->
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result<>(Status.RJC, errorCode, null));
        };
    }

}
