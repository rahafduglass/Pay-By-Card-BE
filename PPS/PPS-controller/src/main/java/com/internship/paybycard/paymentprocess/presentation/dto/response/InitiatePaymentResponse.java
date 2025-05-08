package com.internship.paybycard.paymentprocess.presentation.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InitiatePaymentResponse {
    @NotBlank
    @Size(min = 36, max = 36)
    private String referenceNumber;
}
