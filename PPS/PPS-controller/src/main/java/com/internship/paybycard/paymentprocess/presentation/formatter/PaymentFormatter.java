package com.internship.paybycard.paymentprocess.presentation.formatter;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.response.InitiatePaymentUseCaseResponse;
import com.internship.paybycard.paymentprocess.presentation.dto.response.InitiatePaymentResponse;
import org.springframework.stereotype.Component;

@Component
public class PaymentFormatter {
    public InitiatePaymentResponse toInitiatePaymentResponse(InitiatePaymentUseCaseResponse result) {
        return new InitiatePaymentResponse(result.getMessage(), result.getReferenceNumber());
    }
}
