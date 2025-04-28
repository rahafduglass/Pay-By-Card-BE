package com.internship.paybycard.paymentprocess.core.domain.model;

import java.math.BigDecimal;

public interface PaymentModel {
    Long getPaymentId();
    String getReferenceNumber();
    String getItems();
    BigDecimal getAmount();
    Boolean getConfirmed();
    String getClientName();
    String getCardNumber();
    String getClientEmail();

    void setPaymentId(Long id);
    void setReferenceNumber(String referenceNumber);
    void setItems(String items);
    void setAmount(BigDecimal amount);
    void setConfirmed(Boolean confirmed);
    void setClientName(String clientName);
    void setCardNumber(String cardNumber);
    void setClientEmail(String clientEmail);
}
