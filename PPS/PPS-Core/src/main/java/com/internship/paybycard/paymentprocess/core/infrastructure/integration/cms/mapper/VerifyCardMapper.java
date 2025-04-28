package com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.mapper;

import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.command.VerifyCardCommand;
import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.model.VerifyCardDto;

public interface VerifyCardMapper {
    VerifyCardDto commandToModel(VerifyCardCommand verifyCardCommand);
}
