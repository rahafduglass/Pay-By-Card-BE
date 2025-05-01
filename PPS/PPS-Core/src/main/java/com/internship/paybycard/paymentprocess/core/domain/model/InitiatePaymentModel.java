package com.internship.paybycard.paymentprocess.core.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.dto.PaymentDto;
import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.dto.VerifyCardDto;

import java.math.BigDecimal;

public interface InitiatePaymentModel {

    PaymentDto initiate();
    boolean validatePayment();

    String getItems();
    BigDecimal getAmount();
    String getClientName();
    VerifyCardDto getCard();
}
