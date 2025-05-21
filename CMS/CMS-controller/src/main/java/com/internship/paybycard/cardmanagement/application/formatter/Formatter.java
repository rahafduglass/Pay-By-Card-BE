package com.internship.paybycard.cardmanagement.application.formatter;

import com.internship.paybycard.cardmanagement.application.dtos.response.GetCardResponse;
import com.internship.paybycard.cardmanagement.core.dto.CardDto;
import org.springframework.stereotype.Component;

@Component
public class Formatter {
    public GetCardResponse toGetCardResponse(CardDto cardDto) {
        GetCardResponse getCardResponse = new GetCardResponse();
        getCardResponse.setCvv(cardDto.getCVV());
        getCardResponse.setCardNumber(cardDto.getCardNumber());
        getCardResponse.setExpiryDate(cardDto.getExpiryDate());
        return getCardResponse;
    }
}
