package com.internship.paybycard.paymentprocess.core.domain.model;

import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.model.CardModel;

import java.math.BigDecimal;

public interface InitiatePaymentModel {

    void validatePayment();
    void process();

    String getItems();
    BigDecimal getAmount();
    String getClientName();
    CardModel getCard();

    void setCard(CardModel cardModel);
    void setClientName(String clientName);
    void setAmount(BigDecimal amount);
    void setItems(String items);
}
