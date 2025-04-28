package com.internship.paybycard.paymentprocess.core.domain.model;

import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.model.CardModel;

import java.math.BigDecimal;

public interface PaymentModel {
    Long getPaymentId();
    String getReferenceNumber();
    String getItems();
    BigDecimal getAmount();
    Boolean getConfirmed();
    CardModel getCard();

    void setPaymentId(Long id);
    void setReferenceNumber(String referenceNumber);
    void setItems(String items);
    void setAmount(BigDecimal amount);
    void setConfirmed(Boolean confirmed);
    void setCard(CardModel card);
}
