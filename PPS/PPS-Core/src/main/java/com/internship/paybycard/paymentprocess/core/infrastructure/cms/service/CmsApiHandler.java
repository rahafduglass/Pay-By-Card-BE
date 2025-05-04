package com.internship.paybycard.paymentprocess.core.infrastructure.cms.service;

import com.internship.paybycard.paymentprocess.core.infrastructure.cms.model.CardDto;
import com.internship.paybycard.paymentprocess.core.infrastructure.cms.dto.VerifyCardDto;

import java.math.BigDecimal;

public interface CmsApiHandler {
    CardDto verifyCard(VerifyCardDto verifyCardDto) ;
    void pay(VerifyCardDto verifyCardDto, BigDecimal amount);
}
