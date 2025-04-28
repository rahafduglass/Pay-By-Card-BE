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
}
