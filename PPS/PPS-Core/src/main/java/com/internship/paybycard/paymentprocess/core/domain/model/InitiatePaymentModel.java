package com.internship.paybycard.paymentprocess.core.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.dto.PaymentDto;
import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.model.VerifyCardDto;

import java.math.BigDecimal;

public interface InitiatePaymentModel {

    PaymentDto process();

    String getItems();
    BigDecimal getAmount();
    String getClientName();
    VerifyCardDto getCard();
}
