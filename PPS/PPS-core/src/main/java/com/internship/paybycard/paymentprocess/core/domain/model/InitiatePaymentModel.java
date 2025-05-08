package com.internship.paybycard.paymentprocess.core.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.PaymentDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;

import java.math.BigDecimal;

public interface InitiatePaymentModel {

    PaymentDto process() throws IllegalAccessException;
    boolean validatePayment();

    // todo no need for the below getters they are not used
    String getItems();
    BigDecimal getAmount();
    String getClientName();
    VerifyCardDto getCard();
}
