package com.internship.paybycard.cardmanagement.persistence.dao;


import com.internship.paybycard.cardmanagement.persistence.entity.CardEntity;
import com.internship.paybycard.cardmanagement.persistence.jpa.CardJpaRepository;
import com.internship.paybycard.core.dao.CardDao;
import com.internship.paybycard.core.exception.CardNotFoundException;
import com.internship.paybycard.core.mapper.CardMapper;
import com.internship.paybycard.core.model.CardModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.smartcardio.Card;
import java.time.LocalDate;


@Repository
@RequiredArgsConstructor
public class CardDaoImpl implements CardDao<CardModel> {

    private final CardJpaRepository cardJpaRepository;

    @Qualifier("cardEntityMapperImpl")
    private final CardMapper<CardModel, CardEntity> cardEntityMapper;

    @Override
    public void saveCard(CardModel cardModel) {
        cardJpaRepository.save(cardEntityMapper.reverseTo(cardModel));
    }

    @Override
    public boolean validateCard(String cardNumber, String cvv, LocalDate expiryDate) {
        if (cardJpaRepository.findByCardNumberAndCvvAndExpiryDate(cardNumber, cvv, expiryDate).isPresent()) return true;
        return false;

    }

    @Override
    public boolean updateCard(CardModel cardModel) {
        CardEntity card = cardJpaRepository.findByCardNumberAndCvvAndExpiryDate(cardModel.getCardNumber(),
                cardModel.getCVV(),
                cardModel.getExpiryDate()).orElseThrow(() -> new CardNotFoundException("persistence error: invalid card info"));
        return cardJpaRepository.updateCardBalanceAndClientEmailAndClientName(cardModel.getBalance(), cardModel.getClientEmail(), cardModel.getClientName(),card.getId()) > 0;
    }
}
