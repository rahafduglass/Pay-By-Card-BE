package com.internship.paybycard.paymentprocess.core.domain.command;

import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.command.VerifyCardCommand;

import java.math.BigDecimal;

public interface InitiatePaymentCommand {
    String getItems();
    BigDecimal getAmount();
    String getClientName();
    VerifyCardCommand getCard();

    void setCard(VerifyCardCommand verifyCardCommand);
    void setClientName(String clientName);
    void setAmount(BigDecimal amount);
    void setItems(String items);
}
