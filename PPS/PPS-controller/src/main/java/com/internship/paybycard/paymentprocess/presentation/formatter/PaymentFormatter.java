package com.internship.paybycard.paymentprocess.presentation.formatter;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.response.InitiatePaymentUseCaseResponse;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.core.domain.result.Status;
import com.internship.paybycard.paymentprocess.presentation.dto.response.InitiatePaymentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Component
public class PaymentFormatter {
    public ResponseEntity<InitiatePaymentResponse> toInitiatePaymentResponse(Result<InitiatePaymentUseCaseResponse> result) {
        if (result.getStatus().equals(Status.ACT)) {
            InitiatePaymentResponse initiatePaymentResponse = new InitiatePaymentResponse(result.getData().getMessage(), result.getData().getReferenceNumber());
            return createInitiateResponseEntity(CREATED, initiatePaymentResponse);
        } else {
            InitiatePaymentResponse rejectedResponse = new InitiatePaymentResponse(result.getErrorCode().toString(), null);
            return createInitiateResponseEntity(UNPROCESSABLE_ENTITY, rejectedResponse);
        }
    }

    private ResponseEntity<InitiatePaymentResponse> createInitiateResponseEntity(HttpStatus httpStatus, InitiatePaymentResponse body) {
        return ResponseEntity
                .status(httpStatus)
                .body(body);
    }

}
