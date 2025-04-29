package com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.model;

import java.time.LocalDate;

public interface VerifyCardDto {
    String getCardNumber();
    String getCVV();
    LocalDate getExpiryDate();

    void setExpiryDate(LocalDate expiryDate);
    void setCardNumber(String cardNumber);
    void setCVV(String cvv);
}
