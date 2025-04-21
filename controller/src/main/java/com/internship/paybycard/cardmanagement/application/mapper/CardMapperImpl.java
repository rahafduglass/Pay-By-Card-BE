package com.internship.paybycard.cardmanagement.application.mapper;

import com.internship.paybycard.cardmanagement.application.interactors.CardRequest;
import com.internship.paybycard.core.mapper.CardMapper;
import com.internship.paybycard.core.model.CardModel;
import org.springframework.stereotype.Component;

@Component
public class CardMapperImpl implements CardMapper<CardModel,CardRequest> {

    @Override
    public CardModel mapTo(CardRequest cardRequest) {
        if(cardRequest == null ) {
            return null;
        }
        CardModel cardModel = new CardModel();
        cardModel.setBalance(cardRequest.getBalance());
        cardModel.setClientEmail(cardRequest.getClientEmail());
        cardModel.setClientName(cardRequest.getClientName());
        return cardModel;
    }

    @Override
    public CardRequest reverseTo(CardModel cardModel) {
        if(cardModel == null ) {
            return null;
        }
        CardRequest cardRequest = new CardRequest();
        cardRequest.setBalance(cardModel.getBalance());
        cardRequest.setClientEmail(cardModel.getClientEmail());
        cardRequest.setClientName(cardModel.getClientName());
        return cardRequest;
    }
}
