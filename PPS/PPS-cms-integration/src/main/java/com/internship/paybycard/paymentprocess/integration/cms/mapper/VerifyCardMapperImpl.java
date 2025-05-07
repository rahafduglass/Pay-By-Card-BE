package com.internship.paybycard.paymentprocess.integration.cms.mapper;

import com.internship.paybycard.paymentprocess.core.domain.dto.card.VerifyCardDtoImpl;
import com.internship.paybycard.paymentprocess.core.domain.dto.card.command.VerifyCardDetails;
import com.internship.paybycard.paymentprocess.core.domain.mapper.card.VerifyCardMapper;
import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;
import org.springframework.stereotype.Component;


@Component
public class VerifyCardMapperImpl implements VerifyCardMapper {

    @Override
    public VerifyCardDto commandToDto(VerifyCardDetails verifyCardDetails) {
        if(verifyCardDetails == null) {return null;}
        VerifyCardDtoImpl verifyCardDto= new VerifyCardDtoImpl();
        verifyCardDto.setCardNumber(verifyCardDetails.getCardNumber());
        verifyCardDto.setExpiryDate(verifyCardDetails.getExpiryDate());
        verifyCardDto.setCVV(verifyCardDetails.getCVV());
        return verifyCardDto;
    }
}
