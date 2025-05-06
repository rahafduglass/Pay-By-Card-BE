package com.internship.paybycard.paymentprocess.core.domain.model;

import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;

import java.math.BigDecimal;

public interface CompletePaymentModel {
    void verifyOTP();
    void pay();

    String getReferenceNumber();
    String getOTP();
    BigDecimal getAmount();
    VerifyCardDto getVerifyCardDto();
}
