package com.internship.paybycard.cardmanagement.persistence.dao;


import com.internship.paybycard.cardmanagement.persistence.entity.CardEntity;
import com.internship.paybycard.cardmanagement.persistence.jpa.CardJpaRepository;
import com.internship.paybycard.core.dao.CardDao;
import com.internship.paybycard.core.exception.CardCreationException;
import com.internship.paybycard.core.exception.CardNotFoundException;
import com.internship.paybycard.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.core.logger.CardDaoLogger;
import com.internship.paybycard.core.logger.CardServiceLogger;
import com.internship.paybycard.core.mapper.CardMapper;
import com.internship.paybycard.core.model.CardModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
@RequiredArgsConstructor
public class CardDaoImpl implements CardDao {

    private final CardJpaRepository cardJpaRepository;

    private final CardDaoLogger LOGGER;

    @Qualifier("cardEntityMapperImpl")
    private final CardMapper<CardModel, CardEntity> cardEntityMapper;

    @Override
    public void saveCard(CardModel cardModel) {
        LOGGER.info("CardDao: Saving Card in DB with Jpa Repository");
        if (cardJpaRepository.save(cardEntityMapper.reverseTo(cardModel)).getId() == null) {
            LOGGER.debug("CardDao: saveCard() card couldn't save ");
            throw new CardCreationException("couldn't save card");
        }
    }

    @Override
    public void findCard(String cardNumber, String cvv, LocalDate expiryDate) {
        LOGGER.info("CardDao: Finding Card in DB with Jpa Repository");
        if (!(cardJpaRepository.findByCardNumberAndCvvAndExpiryDate(cardNumber, cvv, expiryDate).isPresent())) {
            LOGGER.debug("CardDao: findCard() card not found | invalid card info");
            throw new CardNotFoundException("invalid card :");
        }
    }

    @Override
    public void deleteCard(ValidateCardInteractor card) {
        LOGGER.info("CardDao: DELETING Card from DB with Jpa Repository");
        if (!(cardJpaRepository.deleteByCardNumber(card.getCardNumber()) > 0)) {
            LOGGER.debug("CardDao: deleteCard() card not found | probably invalid card info");
            throw new RuntimeException("invalid card info");
        }
    }

    @Override
    public void updateCard(UpdateCardInteractor cardModel) {
        LOGGER.info("CardDao: Updating Card in DB with Jpa Repository");
        if (!(cardJpaRepository.updateCardBalanceAndClientEmailAndClientNameByCvvAndCardNumber(cardModel.getBalance(), cardModel.getClientEmail(), cardModel.getClientName(), cardModel.getCvv(), cardModel.getCardNumber()) > 0)) {
            LOGGER.debug("CardDao: updateCard() card not found | probably invalid card info");
            throw new RuntimeException("invalid card info");
        }
    }
}
