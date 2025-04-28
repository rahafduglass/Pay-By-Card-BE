package com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.service;

import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.model.CardModel;
import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.model.VerifyCardModel;

import java.math.BigDecimal;

public interface CmsService {
    CardModel verifyCard(VerifyCardModel verifyCardModel);
    void pay(VerifyCardModel verifyCardModel, BigDecimal amount);
}
