package com.internship.paybycard.paymentprocess.presentation.dto.request.card;

import com.internship.paybycard.paymentprocess.core.domain.dto.card.command.VerifyCardDetails;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class VerifyCardDetailsImpl implements VerifyCardDetails {

    @NotBlank
    @Size(min = 36, max = 36)
    private String cardNumber;
    @NotBlank
    @Size(min = 4, max = 4)
    private String CVV;
    @NotBlank
    @NotNull
    private LocalDate expiryDate;
}
