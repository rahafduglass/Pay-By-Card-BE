package com.internship.paybycard.paymentprocess.core.domain.dto.payment;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
public class NullPaymentDto implements PaymentDto {
    private final Long id = -1L;
    private final String referenceNumber = "";
    private final String items = "";
    private final BigDecimal amount = BigDecimal.ZERO;
    private final String cardNumber = "";
    private final String clientName = "";
    private final String clientEmail = "";
    private final Boolean confirmed = false;
}
