package com.internship.paybycard.core.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CardModel {

    boolean isNull();

    Long getId();

    BigDecimal getBalance();

    LocalDate getExpiryDate();

    String getClientEmail();

    String getClientName();

    String getCVV();

    String getCardNumber();

}
