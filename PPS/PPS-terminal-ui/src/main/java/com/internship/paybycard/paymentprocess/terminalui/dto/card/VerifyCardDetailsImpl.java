package com.internship.paybycard.paymentprocess.terminalui.dto.card;

import com.internship.paybycard.paymentprocess.core.domain.dto.card.command.VerifyCardDetails;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCardDetailsImpl implements VerifyCardDetails {

    @NotBlank
    @Size(min = 36, max = 36)
    private String cardNumber;
    @NotBlank
    @Size(min = 3, max = 3)
    private String CVV;
    @NotNull
    private LocalDate expiryDate;
}
