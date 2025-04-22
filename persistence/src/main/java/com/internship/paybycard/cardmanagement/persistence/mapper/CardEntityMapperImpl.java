package com.internship.paybycard.cardmanagement.persistence.mapper;

import com.internship.paybycard.cardmanagement.persistence.entity.CardEntity;
import com.internship.paybycard.core.mapper.CardMapper;
import com.internship.paybycard.core.model.CardModel;
import org.springframework.stereotype.Component;

@Component
public class CardEntityMapperImpl implements CardMapper<CardModel,CardEntity> {
    @Override
    public CardEntity reverseTo(CardModel cardModel){
        if(cardModel.isNull() ) {
            throw new RuntimeException("CardEntityMapper: cant map, cardModel is null");
        }

        CardEntity cardEntity = new CardEntity();
        cardEntity.setBalance(cardModel.getBalance());
        cardEntity.setCardNumber(cardModel.getCardNumber());
        cardEntity.setCVV(cardModel.getCVV());
        cardEntity.setExpiryDate(cardModel.getExpiryDate());
        cardEntity.setClientEmail(cardModel.getClientEmail());
        cardEntity.setClientName(cardModel.getClientName());
        return cardEntity;
    }

    @Override
    public CardModel mapTo(CardEntity cardEntity) {
        return null;
    }
}
