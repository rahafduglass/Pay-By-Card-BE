package com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CardModel {
    void verify();
    void pay(BigDecimal amount);

    String getCardNumber();
    String getCVV();
    LocalDate getExpiryDate();
    String getClientName();
    String getClientEmail();
    BigDecimal getBalance();

    void setExpiryDate(LocalDate expiryDate);
    void setCardNumber(String cardNumber);
    void setCVV(String cvv);
    void setClientName(String clientName);
    void setClientEmail(String clientEmail);
    void setBalance(String balance);
}
