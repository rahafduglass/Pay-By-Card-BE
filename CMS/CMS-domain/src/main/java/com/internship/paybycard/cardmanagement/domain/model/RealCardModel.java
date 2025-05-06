package com.internship.paybycard.cardmanagement.domain.model;

import com.internship.paybycard.cardmanagement.core.model.CardModel;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RealCardModel implements CardModel {
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
    public boolean equals(CardModel cardModel) {
        return cardModel.getId().equals(this.id)
                && cardModel.getCardNumber().equals(this.cardNumber)
                && cardModel.getCVV().equals(this.CVV)
                && cardModel.getClientName().equals(this.clientName)
                && cardModel.getClientEmail().equals(this.clientEmail)
                && cardModel.getExpiryDate().equals(this.expiryDate)
                && cardModel.getBalance().equals(this.balance);
    }
}
