package com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.mapper;

import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.command.VerifyCardCommand;
import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.model.CardModel;

public interface CardMapper {
    CardModel commandToModel(VerifyCardCommand verifyCardCommand);
}
