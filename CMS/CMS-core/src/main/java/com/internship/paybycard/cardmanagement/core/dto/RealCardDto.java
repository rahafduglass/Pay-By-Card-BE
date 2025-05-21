package com.internship.paybycard.cardmanagement.core.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RealCardDto implements CardDto {
    private Long id;
    private String cardNumber;
    private String CVV;
    private String clientName;
    private String clientEmail;
    private LocalDate expiryDate;
    private BigDecimal balance;

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean equals(CardDto cardDto) {
        return cardDto.getId().equals(this.id)
                && cardDto.getCardNumber().equals(this.cardNumber)
                && cardDto.getCVV().equals(this.CVV)
                && cardDto.getClientName().equals(this.clientName)
                && cardDto.getClientEmail().equals(this.clientEmail)
                && cardDto.getExpiryDate().equals(this.expiryDate)
                && cardDto.getBalance().equals(this.balance);
    }
}
