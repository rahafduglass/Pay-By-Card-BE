package com.internship.paybycard.paymentprocess.presentation.dto.request.payment;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.VerifyPaymentCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VerifyPaymentCommandImpl implements VerifyPaymentCommand {

    @NotBlank
    @Size(min = 36, max = 36,message="payment reference must be 36 characters")
    private String paymentReference;

}
