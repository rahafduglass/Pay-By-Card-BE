package com.internship.paybycard.cardmanagement.persistence.mapper;

import com.internship.paybycard.cardmanagement.core.model.CardDto;
import com.internship.paybycard.cardmanagement.core.model.NullCardDto;
import com.internship.paybycard.cardmanagement.core.model.RealCardDto;
import com.internship.paybycard.cardmanagement.persistence.entity.CardEntity;
import com.internship.paybycard.cardmanagement.core.mapper.CardMapper;
import org.springframework.stereotype.Component;

@Component
public class CardEntityMapperImpl implements CardMapper<CardDto,CardEntity> {
    @Override
    public CardEntity mapTo(CardDto cardDto){
        if(cardDto.isNull() ) {
            throw new RuntimeException("CardEntityMapper: cant map, card is null");
        }

        CardEntity cardEntity = new CardEntity();
        cardEntity.setBalance(cardDto.getBalance());
        cardEntity.setCardNumber(cardDto.getCardNumber());
        cardEntity.setCVV(cardDto.getCVV());
        cardEntity.setExpiryDate(cardDto.getExpiryDate());
        cardEntity.setClientEmail(cardDto.getClientEmail());
        cardEntity.setClientName(cardDto.getClientName());
        return cardEntity;
    }

    @Override
    public CardDto reverseTo(CardEntity cardEntity) {
        if(cardEntity == null) {
           return new NullCardDto();
        }
        RealCardDto cardModel= new RealCardDto();
        cardModel.setId(cardEntity.getId());
        cardModel.setBalance(cardEntity.getBalance());
        cardModel.setCardNumber(cardEntity.getCardNumber());
        cardModel.setCVV(cardEntity.getCVV());
        cardModel.setExpiryDate(cardEntity.getExpiryDate());
        cardModel.setClientEmail(cardEntity.getClientEmail());
        cardModel.setClientName(cardEntity.getClientName());
        return cardModel;
    }
}
