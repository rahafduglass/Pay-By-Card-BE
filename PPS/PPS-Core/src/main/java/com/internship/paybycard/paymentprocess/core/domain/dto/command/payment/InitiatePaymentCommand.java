package com.internship.paybycard.paymentprocess.core.domain.dto.command.payment;

import com.internship.paybycard.paymentprocess.core.domain.dto.command.card.VerifyCardCommand;

import java.math.BigDecimal;

public interface InitiatePaymentCommand {
    String getItems();
    BigDecimal getAmount();
    String getClientName();
    VerifyCardCommand getCard();

}
