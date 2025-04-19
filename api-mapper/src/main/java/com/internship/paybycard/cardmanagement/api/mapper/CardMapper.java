package com.internship.paybycard.cardmanagement.api.mapper;

import com.internship.paybycard.cardmanagement.api.dto.CardRequest;
import com.internship.paybycard.cardmanagement.domain.model.CardModel;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {
    public CardModel requestToModel(CardRequest cardRequest) {
        if(cardRequest == null ) {
            return null;
        }
        CardModel cardModel = new CardModel();
        cardModel.setBalance(cardRequest.getBalance());
        cardModel.setClientEmail(cardRequest.getClientEmail());
        cardModel.setClientName(cardRequest.getClientName());
        return cardModel;
    }
}
