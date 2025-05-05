package com.internship.paybycard.paymentprocess.integration.cms.mapper;

import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;
import com.internship.paybycard.paymentprocess.core.domain.dto.command.card.VerifyCardCommand;
import com.internship.paybycard.paymentprocess.core.domain.mapper.card.VerifyCardMapper;
import com.internship.paybycard.paymentprocess.integration.cms.dto.VerifyCardDtoImpl;
import org.springframework.stereotype.Component;


@Component
public class VerifyCardMapperImpl implements VerifyCardMapper {

    @Override
    public VerifyCardDto commandToDto(VerifyCardCommand verifyCardCommand) {
        if(verifyCardCommand == null) {return null;}
        VerifyCardDto verifyCardDto= new VerifyCardDtoImpl();
        verifyCardDto.setCardNumber(verifyCardCommand.getCardNumber());
        verifyCardDto.setExpiryDate(verifyCardCommand.getExpiryDate());
        verifyCardDto.setCVV(verifyCardCommand.getCVV());
        return verifyCardDto;
    }
}
