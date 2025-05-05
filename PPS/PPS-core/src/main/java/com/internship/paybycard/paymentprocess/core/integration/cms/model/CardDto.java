package com.internship.paybycard.paymentprocess.core.integration.cms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {
    private Long id;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String clientName;
    private String clientEmail;
    private BigDecimal balance;
}
