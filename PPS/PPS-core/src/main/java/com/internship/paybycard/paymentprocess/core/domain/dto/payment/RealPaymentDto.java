package com.internship.paybycard.paymentprocess.core.domain.dto.payment;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;


@Data
@EqualsAndHashCode(callSuper = false)
public class RealPaymentDto implements PaymentDto {
    private Long id;
    private String referenceNumber;
    private String items;
    private BigDecimal amount;
    private String cardNumber;
    private String clientName;
    private String clientEmail;
    private Boolean confirmed;

}
