package com.internship.paybycard.paymentprocess.core.domain.model;

import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;

public interface CompletePaymentModel {
    void verifyOTP();
    void pay();

    String getReferenceNumber();
    String getOTP();
    VerifyCardDto getVerifyCardDto();
}
