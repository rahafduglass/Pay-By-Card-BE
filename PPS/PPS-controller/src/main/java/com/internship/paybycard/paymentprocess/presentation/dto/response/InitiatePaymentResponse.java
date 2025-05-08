package com.internship.paybycard.paymentprocess.presentation.dto.response;

import lombok.Data;

@Data
public class InitiatePaymentResponse {
    // todo size and not null rules
    private String referenceNumber;
}
