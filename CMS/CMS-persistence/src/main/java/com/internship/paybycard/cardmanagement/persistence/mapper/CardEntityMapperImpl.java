package com.internship.paybycard.cardmanagement.persistence.mapper;

import com.internship.paybycard.cardmanagement.persistence.entity.CardEntity;
import com.internship.paybycard.cardmanagement.core.mapper.CardMapper;
import com.internship.paybycard.cardmanagement.core.model.CardModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
// todo unit test
public class CardEntityMapperImpl implements CardMapper<CardModel,CardEntity> {
    @Override
    public CardEntity mapTo(CardModel cardModel){
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
    public CardModel reverseTo(CardEntity cardEntity) {
        if(cardEntity == null) {
            throw new RuntimeException("CardEntityMapper: cant map, cardEntity is null");
        }
        CardModel cardModel= new CardModel() {
            @Override
            public boolean isNull() {
                return false;
            }

            @Override
            public Long getId() {
                return cardEntity.getId();
            }

            @Override
            public BigDecimal getBalance() {
                return cardEntity.getBalance();
            }

            @Override
            public LocalDate getExpiryDate() {
                return cardEntity.getExpiryDate();
            }

            @Override
            public String getClientEmail() {
                return cardEntity.getClientEmail();
            }

            @Override
            public String getClientName() {
                return cardEntity.getClientName();
            }

            @Override
            public String getCVV() {
                return cardEntity.getCVV();
            }

            @Override
            public String getCardNumber() {
                return cardEntity.getCardNumber();
            }
        };
        return cardModel;
    }
}
