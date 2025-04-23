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

}
