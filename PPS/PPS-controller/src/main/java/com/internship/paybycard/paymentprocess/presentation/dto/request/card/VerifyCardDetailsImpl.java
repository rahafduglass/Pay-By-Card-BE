package com.internship.paybycard.paymentprocess.presentation.dto.request.card;

import com.internship.paybycard.paymentprocess.core.domain.dto.card.command.VerifyCardDetails;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VerifyCardDetailsImpl implements VerifyCardDetails {
// todo size and not null rules
    private String cardNumber;
    private String CVV;
    private LocalDate expiryDate;
}
