package com.internship.paybycard.core.interactor;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface UpdateCardInteractor {
    String getCvv();

    String getCardNumber();

    String getClientEmail();

    String getClientName();

    BigDecimal getBalance();

    LocalDate getExpiryDate();


    void setCvv(String cvv);

    void setCardNumber(String cardNumber);

    void setClientEmail(String clientEmail);

    void setClientName(String clientName);

    void setBalance(BigDecimal balance);

    void setExpiryDate(LocalDate expiryDate);

}
