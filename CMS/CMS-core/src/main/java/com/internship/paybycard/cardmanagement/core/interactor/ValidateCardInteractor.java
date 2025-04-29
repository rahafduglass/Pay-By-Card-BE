package com.internship.paybycard.cardmanagement.core.interactor;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ValidateCardInteractor {
    String getCardNumber();
    String getCVV();
    LocalDate getExpiryDate();

}

