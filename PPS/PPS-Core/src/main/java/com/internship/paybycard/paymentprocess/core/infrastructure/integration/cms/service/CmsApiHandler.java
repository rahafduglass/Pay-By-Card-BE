package com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.service;

import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.model.CardModel;
import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.model.VerifyCardDto;

import java.math.BigDecimal;

public interface CmsApiHandler {
    CardModel verifyCard(VerifyCardDto verifyCardDto);
    void pay(VerifyCardDto verifyCardDto, BigDecimal amount);
}
