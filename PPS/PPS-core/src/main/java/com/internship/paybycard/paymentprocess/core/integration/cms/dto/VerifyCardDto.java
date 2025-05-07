package com.internship.paybycard.paymentprocess.core.integration.cms.dto;

import java.time.LocalDate;

public interface VerifyCardDto {
    String getCardNumber();
    String getCVV();
    LocalDate getExpiryDate();

    //todo no setters
    void setExpiryDate(LocalDate expiryDate);
    void setCardNumber(String cardNumber);
    void setCVV(String cvv);
}
