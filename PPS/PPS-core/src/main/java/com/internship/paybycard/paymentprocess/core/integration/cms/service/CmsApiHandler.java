package com.internship.paybycard.paymentprocess.core.integration.cms.service;

import com.internship.paybycard.paymentprocess.core.integration.cms.model.CardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;

import java.math.BigDecimal;

public interface CmsApiHandler {
    CardDto verifyCard(VerifyCardDto verifyCardDto) ;
    void pay(VerifyCardDto verifyCardDto, BigDecimal amount);
}
