package com.internship.paybycard.cardmanagement.persistence.mapper;

import com.internship.paybycard.cardmanagement.persistence.entity.CardEntity;
import com.internship.paybycard.core.mapper.CardMapper;
import com.internship.paybycard.core.model.Card;
import com.internship.paybycard.core.model.CardModel;
import com.internship.paybycard.core.model.NullCardModel;
import org.springframework.stereotype.Component;

@Component
public class CardEntityMapperImpl implements CardMapper<Card,CardEntity> {
    @Override
    public CardEntity reverseTo(Card card){
        if(card == null ) {
            return null;
        }

        CardModel cardModel = (CardModel) card;
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
    public Card mapTo(CardEntity cardEntity) {
        if(cardEntity == null ) {
            return new NullCardModel();
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
