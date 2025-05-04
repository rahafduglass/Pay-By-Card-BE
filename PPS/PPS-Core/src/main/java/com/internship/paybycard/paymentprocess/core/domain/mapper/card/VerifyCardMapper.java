package com.internship.paybycard.paymentprocess.core.domain.mapper.card;

import com.internship.paybycard.paymentprocess.core.domain.dto.command.card.VerifyCardCommand;
import com.internship.paybycard.paymentprocess.core.infrastructure.cms.dto.VerifyCardDto;

public interface VerifyCardMapper {
    VerifyCardDto commandToDto(VerifyCardCommand verifyCardCommand);
}
