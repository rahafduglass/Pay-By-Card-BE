package com.internship.paybycard.paymentprocess.core.domain.dto.payment.command;

import com.internship.paybycard.paymentprocess.core.domain.dto.card.command.VerifyCardDetails;

import java.math.BigDecimal;

public interface InitiatePaymentCommand {
    String getItems();
    BigDecimal getAmount();
    String getClientName();
    VerifyCardDetails getCard();

}
