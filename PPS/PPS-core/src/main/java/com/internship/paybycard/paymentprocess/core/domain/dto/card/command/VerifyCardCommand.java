package com.internship.paybycard.paymentprocess.core.domain.dto.card.command;

import java.time.LocalDate;

//todo rename to card details
public interface VerifyCardCommand {
    String getCardNumber();
    String getCVV();
    LocalDate getExpiryDate();
}
