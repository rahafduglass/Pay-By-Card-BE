package com.internship.paybycard.paymentprocess.presentation.dto.request.payment;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.presentation.dto.request.card.VerifyCardDetailsImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NotNull
public class InitiatePaymentCommandImpl implements InitiatePaymentCommand {
    @NotBlank
    private String items;
    @NotNull
    @DecimalMin(value = "1")
    @DecimalMax(value = "10000.00")
    private BigDecimal amount;
    @NotBlank
    @Size(min = 5, max = 20)
    private String clientName;
    @NotNull
    @Valid
    private VerifyCardDetailsImpl card;
}
