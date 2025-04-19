package com.internship.paybycard.cardmanagement.mapper;

import com.internship.paybycard.cardmanagement.domain.model.CardModel;
import com.internship.paybycard.cardmanagement.entity.CardEntity;
import org.springframework.stereotype.Component;

@Component
public class CardEntityMapper {
    public CardEntity modelToEntity(CardModel cardModel){
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

    public CardModel entityToModel(CardEntity cardEntity) {
        if(cardEntity == null ) {
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
