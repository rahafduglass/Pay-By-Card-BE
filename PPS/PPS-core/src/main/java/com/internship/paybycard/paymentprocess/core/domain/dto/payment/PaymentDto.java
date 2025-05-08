package com.internship.paybycard.paymentprocess.core.domain.dto.payment;

import java.math.BigDecimal;

public interface PaymentDto {
    default boolean isNull() {
        return this instanceof NullPaymentDto;
    }
    Long getId();

    String getReferenceNumber();

    String getItems();

    BigDecimal getAmount();

    String getCardNumber();

    String getClientName();

    String getClientEmail();

    Boolean getConfirmed();
}
