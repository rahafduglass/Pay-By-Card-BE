package com.internship.paybycard.cardmanagement.core.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class NullCardDto implements CardDto {

    Long id=-1L;
    String cardNumber="0000000000000000";
    String CVV = "000";
    String clientName = "Unknown";
    String clientEmail = "null@null.com";
    LocalDate expiryDate = LocalDate.of(1970, 1, 1); // Epoch date
    BigDecimal balance = BigDecimal.ZERO;

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public boolean equals(CardDto cardDto) {
        return false;
    }
}
