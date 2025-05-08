package com.internship.paybycard.paymentprocess.presentation.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InitiatePaymentResponse {
    @NotBlank
    @Size(min = 36, max = 36)
    private String referenceNumber;
}
