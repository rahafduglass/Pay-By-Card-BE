package com.internship.paybycard.paymentprocess.presentation.dto.request.payment;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.presentation.dto.request.card.VerifyCardDetailsImpl;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Builder
@NotNull
public class InitiatePaymentCommandImpl implements InitiatePaymentCommand {
    @NotBlank
    private String items;
    @NotNull
    private BigDecimal amount;
    @NotBlank
    @Size(min = 5, max = 20)
    private String clientName;
    @NotNull
    private VerifyCardDetailsImpl card;
}
