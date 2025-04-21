package com.internship.paybycard.core.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CardModel implements Card {
    Long id;
    String cardNumber;

    String CVV;
    String clientName;
    String clientEmail;
    LocalDate expiryDate;
    BigDecimal balance;

    @Override
    public boolean isNull() {
        return false;
    }
}
