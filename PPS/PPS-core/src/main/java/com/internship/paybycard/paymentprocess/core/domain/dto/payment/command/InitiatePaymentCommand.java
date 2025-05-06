package com.internship.paybycard.paymentprocess.core.domain.dto.payment.command;

import com.internship.paybycard.paymentprocess.core.domain.dto.card.command.VerifyCardCommand;

import java.math.BigDecimal;

public interface InitiatePaymentCommand {
    String getItems();
    BigDecimal getAmount();
    String getClientName();
    VerifyCardCommand getCard();

}
