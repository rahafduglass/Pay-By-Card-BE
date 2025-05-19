package com.internship.paybycard.paymentprocess.domain.dto.payment.response;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.response.InitiatePaymentUseCaseResponse;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class InitiatePaymentUseCaseResponseImpl implements InitiatePaymentUseCaseResponse {
    private String referenceNumber;
    private String message;

}
