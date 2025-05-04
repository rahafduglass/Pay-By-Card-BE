package com.internship.paybycard.paymentprocess.core.domain.dto.command.card;

import java.time.LocalDate;

public interface VerifyCardCommand {
    String getCardNumber();
    String getCVV();
    LocalDate getExpiryDate();
}
