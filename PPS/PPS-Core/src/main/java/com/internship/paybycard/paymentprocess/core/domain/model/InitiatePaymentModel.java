package com.internship.paybycard.paymentprocess.core.domain.model;

import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.model.VerifyCardModel;

import java.math.BigDecimal;

public interface InitiatePaymentModel {

    PaymentModel process();

    String getItems();
    BigDecimal getAmount();
    String getClientName();
    VerifyCardModel getCard();

    void setCard(VerifyCardModel cardModel);
    void setClientName(String clientName);
    void setAmount(BigDecimal amount);
    void setItems(String items);
}
