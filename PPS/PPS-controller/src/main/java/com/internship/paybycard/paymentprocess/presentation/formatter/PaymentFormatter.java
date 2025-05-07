package com.internship.paybycard.paymentprocess.presentation.formatter;

import com.internship.paybycard.paymentprocess.presentation.dto.response.InitiatePaymentResponse;
import org.springframework.stereotype.Component;

@Component
public class PaymentFormatter {
    public InitiatePaymentResponse toInitiatePaymentResponse(String data) {
        InitiatePaymentResponse response = new InitiatePaymentResponse();
        response.setReferenceNumber(data);
        return response;
    }
}
