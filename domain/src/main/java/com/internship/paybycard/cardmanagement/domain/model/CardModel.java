package com.internship.paybycard.cardmanagement.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CardModel {
    Long id;
    String cardNumber;

    String CVV;
    String clientName;
    String clientEmail;
    LocalDate expiryDate;
    BigDecimal balance;
}
