package com.internship.paybycard.paymentprocess.terminalui.dto.payment.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VerifyPaymentCommandImpl {
    @NotBlank
    @Size(min = 36, max = 36, message = "payment reference must be 36 characters")
    private String paymentReference;
}
