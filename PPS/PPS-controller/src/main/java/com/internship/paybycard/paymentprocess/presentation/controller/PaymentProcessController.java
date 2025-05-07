package com.internship.paybycard.paymentprocess.presentation.controller;

import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.core.domain.result.Status;
import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import com.internship.paybycard.paymentprocess.presentation.controller.dto.command.payment.CompletePaymentCommandImpl;
import com.internship.paybycard.paymentprocess.presentation.controller.dto.command.payment.InitiatePaymentCommandImpl;
import com.internship.paybycard.paymentprocess.presentation.controller.dto.command.payment.VerifyPaymentCommandImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/paymentProcess")
@RequiredArgsConstructor
public class PaymentProcessController {

    private final PaymentProcessUseCase paymentProcessUseCase;

    @PostMapping("/initiate")
    public ResponseEntity<Result<String>> initiatePayment(@RequestBody InitiatePaymentCommandImpl command) {
//        todo create new response for and map it in new layer (formatter) this should be implemetned for all api's paths
        Result<String> result = paymentProcessUseCase.initiatePayment(command);
        if (result.getStatus().name().equals("RJC")) {
            return rejectResponse(result.getErrorCode());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/verify")
    public ResponseEntity<Result<Void>> verifyPayment(@RequestBody VerifyPaymentCommandImpl command) {
        Result<Void> result = paymentProcessUseCase.verifyPayment(command);
        if (result.getStatus().name().equals("RJC")) {
            return rejectResponse(result.getErrorCode());
        }
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/complete")
    public ResponseEntity<Result<Void>> completePayment(@RequestBody CompletePaymentCommandImpl command) {
        Result<Void> result= paymentProcessUseCase.completePayment(command);
        if (result.getStatus().name().equals("RJC")) {
            return rejectResponse(result.getErrorCode());
        }
        return ResponseEntity.noContent().build();
    }



    private <T> ResponseEntity<Result<T>> rejectResponse(ErrorCode errorCode) {
        return switch (errorCode) {
            case INVALID_COMMAND_INPUT, INVALID_PAYMENT_INPUT, INVALID_CARD, EMPTY_REFERENCE_NUMBER,
                 PAYMENT_NOT_FOUND ->
                    ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new Result<>(Status.RJC, errorCode, null));
            default ->
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result<>(Status.RJC, errorCode, null));
        };
    }

}
