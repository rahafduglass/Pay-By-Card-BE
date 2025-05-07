package com.internship.paybycard.paymentprocess.presentation.dto.request.payment;

import com.internship.paybycard.paymentprocess.core.domain.dto.card.VerifyCardDtoImpl;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.CompletePaymentCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CompletePaymentCommandImpl implements CompletePaymentCommand {
    @NotBlank
    @Size(min = 36, max = 36, message = "payment reference must be 36 characters")
    String paymentReference;
    @NotBlank
    @Size(min = 4, max = 4, message = "otp must be 4 characters")
    String OTP;
    @NotNull
    VerifyCardDtoImpl verifyCard;
}
