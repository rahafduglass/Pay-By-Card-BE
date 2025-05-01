package com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.mapper;

import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.dto.command.VerifyCardCommand;
import com.internship.paybycard.paymentprocess.core.infrastructure.integration.cms.dto.VerifyCardDto;

public interface VerifyCardMapper {
    VerifyCardDto commandToDto(VerifyCardCommand verifyCardCommand);
}
