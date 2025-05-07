package com.internship.paybycard.paymentprocess.core.domain.dto.card.command;

import java.time.LocalDate;

public interface VerifyCardDetails {
    String getCardNumber();
    String getCVV();
    LocalDate getExpiryDate();
}
