package com.internship.paybycard.cardmanagement.persistence.mapper;

import com.internship.paybycard.cardmanagement.persistence.entity.CardEntity;
import com.internship.paybycard.core.mapper.CardMapper;
import com.internship.paybycard.core.model.CardModel;
import com.internship.paybycard.core.model.NullCardModel;
import com.internship.paybycard.core.model.RealCardModel;
import org.springframework.stereotype.Component;

@Component
public class CardEntityMapperImpl implements CardMapper<CardModel,CardEntity> {
    @Override
    public CardEntity reverseTo(CardModel cardModel){
        if(cardModel.isNull() ) {
            throw new RuntimeException("CardEntityMapper: cant map, cardModel is null");
        }


        if(!(cardModel instanceof RealCardModel realCardModel)) {
            //dang wrong type
            throw new RuntimeException("argument cardModel should be type RealCardModel");
        }

        CardEntity cardEntity = new CardEntity();
        cardEntity.setBalance(realCardModel.getBalance());
        cardEntity.setCardNumber(realCardModel.getCardNumber());
        cardEntity.setCVV(realCardModel.getCVV());
        cardEntity.setExpiryDate(realCardModel.getExpiryDate());
        cardEntity.setClientEmail(realCardModel.getClientEmail());
        cardEntity.setClientName(realCardModel.getClientName());
        return cardEntity;
    }

    @Override
    public CardModel mapTo(CardEntity cardEntity) {
        if(cardEntity == null ) {
            return new NullCardModel();
        }
        RealCardModel cardModel = new RealCardModel();
        cardModel.setBalance(cardEntity.getBalance());
        cardModel.setCardNumber(cardEntity.getCardNumber());
        cardModel.setCVV(cardEntity.getCVV());
        cardModel.setExpiryDate(cardEntity.getExpiryDate());
        cardModel.setClientEmail(cardEntity.getClientEmail());
        cardModel.setClientName(cardEntity.getClientName());
        return cardModel;
    }
}
