package com.internship.paybycard.paymentprocess.core.domain.dto.command;

import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.dto.command.VerifyCardCommand;

import java.math.BigDecimal;

public interface InitiatePaymentCommand {
    String getItems();
    BigDecimal getAmount();
    String getClientName();
    VerifyCardCommand getCard();

}
