package com.internship.paybycard.cardmanagement.persistence.mapper;

import com.internship.paybycard.cardmanagement.persistence.entity.CardEntity;
import com.internship.paybycard.core.mapper.CardMapper;
import com.internship.paybycard.core.model.CardModel;
import org.springframework.stereotype.Component;

@Component
public class CardEntityMapperImpl implements CardMapper< CardModel,CardEntity> {
    @Override
    public CardEntity reverseMap(CardModel cardModel){
        if(cardModel == null ) {
            return null;
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
    public CardModel mapToModel(CardEntity cardEntity) {
        if(cardEntity == null ) {
//todo return NULL-Pattern (read about it) Insted of NULL
            return null;
        }
        CardModel cardModel = new CardModel();
        cardModel.setBalance(cardEntity.getBalance());
        cardModel.setCardNumber(cardEntity.getCardNumber());
        cardModel.setCVV(cardEntity.getCVV());
        cardModel.setExpiryDate(cardEntity.getExpiryDate());
        cardModel.setClientEmail(cardEntity.getClientEmail());
        cardModel.setClientName(cardEntity.getClientName());
        return cardModel;
    }
}
