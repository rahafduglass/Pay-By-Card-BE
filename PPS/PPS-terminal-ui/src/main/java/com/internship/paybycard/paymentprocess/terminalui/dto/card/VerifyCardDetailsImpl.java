package com.internship.paybycard.paymentprocess.terminalui.dto.card;

import com.internship.paybycard.paymentprocess.core.domain.dto.card.command.VerifyCardDetails;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VerifyCardDetailsImpl implements VerifyCardDetails {

    private String cardNumber;

    private String CVV;

    private LocalDate expiryDate;
}
