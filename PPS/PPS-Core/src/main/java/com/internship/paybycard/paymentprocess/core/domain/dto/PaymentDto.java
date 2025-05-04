package com.internship.paybycard.paymentprocess.core.domain.dto;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class PaymentDto {
    private Long id;
    private String referenceNumber;
    private String items;
    private BigDecimal amount;
    private String cardNumber;
    private String clientName;
    private String clientEmail;
    private Boolean confirmed;
}
