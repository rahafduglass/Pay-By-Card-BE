package com.internship.paybycard.paymentprocess.core.domain.dto.payment.command;

import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;

import java.math.BigDecimal;

public interface CompletePaymentCommand {
    String getPaymentReference();
    String getOTP();
    BigDecimal getAmount();
    VerifyCardDto getVerifyCard();

}
