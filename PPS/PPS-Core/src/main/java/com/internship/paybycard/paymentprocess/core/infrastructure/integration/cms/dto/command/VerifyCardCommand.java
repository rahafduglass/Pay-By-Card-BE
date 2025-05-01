package com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.dto.command;

import java.time.LocalDate;

public interface VerifyCardCommand {
    String getCardNumber();
    String getCVV();
    LocalDate getExpiryDate();

    void setExpiryDate(LocalDate expiryDate);
    void setCardNumber(String cardNumber);
    void setCVV(String cvv);
}
