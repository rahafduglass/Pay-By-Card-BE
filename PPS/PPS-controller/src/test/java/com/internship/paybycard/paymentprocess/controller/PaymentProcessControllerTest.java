package com.internship.paybycard.paymentprocess.controller;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.VerifyPaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.core.domain.result.Status;
import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import com.internship.paybycard.paymentprocess.presentation.controller.PaymentProcessController;
import com.internship.paybycard.paymentprocess.presentation.dto.request.payment.CompletePaymentCommandImpl;
import com.internship.paybycard.paymentprocess.presentation.dto.request.payment.InitiatePaymentCommandImpl;
import com.internship.paybycard.paymentprocess.presentation.dto.request.payment.VerifyPaymentCommandImpl;
import com.internship.paybycard.paymentprocess.presentation.dto.response.InitiatePaymentResponse;
import com.internship.paybycard.paymentprocess.presentation.formatter.PaymentFormatter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentProcessControllerTest {

    @Mock
    private PaymentProcessUseCase paymentProcessUseCase;

    @Mock
    private PaymentFormatter paymentFormatter;

    @InjectMocks
    private PaymentProcessController paymentProcessController;

    @Test
    public void givenValidRequest_whenCallInitiatePayment_thenReturnInitiatePaymentResponseAndHttpsStatus201() {
        String referenceNumber = UUID.randomUUID().toString();
        InitiatePaymentResponse response = new InitiatePaymentResponse("message",referenceNumber);

        when(paymentProcessUseCase.initiatePayment(any(InitiatePaymentCommand.class))).thenReturn(new Result<>(Status.ACT, ErrorCode.NULL, referenceNumber));
        when(paymentFormatter.toInitiatePaymentResponse(any(String.class),any(String.class))).thenReturn(response);
        ResponseEntity<InitiatePaymentResponse> apiResponse = paymentProcessController.initiatePayment(InitiatePaymentCommandImpl.builder().build());
        assertEquals(201, apiResponse.getStatusCodeValue());
        assertEquals(response, apiResponse.getBody());
    }

    @Test
    public void givenInvalidRequest_whenCallInitiatePayment_thenShouldReturnHttpsStatus422() {
        when(paymentProcessUseCase.initiatePayment(any(InitiatePaymentCommandImpl.class))).thenReturn(new Result<String>(Status.RJC, ErrorCode.INVALID_CARD, null));
        ResponseEntity<InitiatePaymentResponse> apiResponse = paymentProcessController.initiatePayment(InitiatePaymentCommandImpl.builder().build());
        assertEquals(422, apiResponse.getStatusCodeValue());
        assertNull(apiResponse.getBody());
    }

    @Test
    public void givenValidRequest_whenCallVerifyPayment_thenReturnHttpStatus204() {
        when(paymentProcessUseCase.verifyPayment(any(VerifyPaymentCommandImpl.class))).thenReturn(new Result<>(Status.ACT, ErrorCode.NULL, null));
        ResponseEntity<Void> apiResponse = paymentProcessController.verifyPayment(new VerifyPaymentCommandImpl());
        assertEquals(204, apiResponse.getStatusCodeValue());
        assertNull(apiResponse.getBody());
    }

    @Test
    public void givenInvalidRequest_whenCallVerifyPayment_thenShouldReturnHttpStatus422() {
        when(paymentProcessUseCase.verifyPayment(any(VerifyPaymentCommand.class))).thenReturn(new Result<>(Status.RJC, ErrorCode.PAYMENT_NOT_FOUND, null));
        ResponseEntity<Void> apiResponse = paymentProcessController.verifyPayment(new VerifyPaymentCommandImpl());
        assertEquals(422, apiResponse.getStatusCodeValue());
        assertNull(apiResponse.getBody());
    }

    @Test
    public void givenValidRequest_whenCallCompletePayment_thenReturnHttpStatus204() {
        when(paymentProcessUseCase.completePayment(any(CompletePaymentCommandImpl.class))).thenReturn(new Result<>(Status.ACT, ErrorCode.NULL, null));
        ResponseEntity<Void> apiResponse = paymentProcessController.completePayment(new CompletePaymentCommandImpl());
        assertEquals(204, apiResponse.getStatusCodeValue());
        assertNull(apiResponse.getBody());
    }

    @Test
    public void givenInValidRequest_whenCallCompletePayment_thenReturnHttpStatus422() {
        when(paymentProcessUseCase.completePayment(any(CompletePaymentCommandImpl.class))).thenReturn(new Result<>(Status.RJC, ErrorCode.INVALID_OR_EXPIRED_OTP, null));
        ResponseEntity<Void> apiResponse = paymentProcessController.completePayment(new CompletePaymentCommandImpl());
        assertEquals(422, apiResponse.getStatusCodeValue());
        assertNull(apiResponse.getBody());
    }

}
