package com.internship.paybycard.paymentprocess.core.domain.dto.payment;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class NullPaymentDto implements PaymentDto {
    private Long id = -1L;
    private String referenceNumber = "";
    private String items = "";
    private BigDecimal amount = BigDecimal.ZERO;
    private String cardNumber = "";
    private String clientName = "";
    private String clientEmail = "";
    private Boolean confirmed = false;

}
