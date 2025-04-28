package com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.mapper;

import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.command.VerifyCardCommand;
import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.model.VerifyCardModel;

public interface VerifyCardMapper {
    VerifyCardModel commandToModel(VerifyCardCommand verifyCardCommand);
}
