package com.internship.paybycard.cardmanagement.persistence.dao;


import com.internship.paybycard.cardmanagement.persistence.entity.CardEntity;
import com.internship.paybycard.cardmanagement.persistence.jpa.CardJpaRepository;
import com.internship.paybycard.core.dao.CardDao;
import com.internship.paybycard.core.exception.CardCreationException;
import com.internship.paybycard.core.exception.CardNotFoundException;
import com.internship.paybycard.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.core.mapper.CardMapper;
import com.internship.paybycard.core.model.CardModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Slf4j
@Repository
@RequiredArgsConstructor
public class CardDaoImpl implements CardDao {

    private final CardJpaRepository cardJpaRepository;


    @Qualifier("cardEntityMapperImpl")
    private final CardMapper<CardModel, CardEntity> cardEntityMapper;

    @Override
    public void saveCard(CardModel cardModel) {
        log.info("Saving Card in DB with Jpa Repository");
        if (cardJpaRepository.save(cardEntityMapper.reverseTo(cardModel)).getId() == null) {
            log.debug("saveCard() card couldn't save ");
            throw new CardCreationException("couldn't save card");
        }
    }

    @Override
    public void findCard(String cardNumber, String cvv, LocalDate expiryDate) {
        log.info("Finding Card in DB with Jpa Repository");
        if (!(cardJpaRepository.findByCardNumberAndCvvAndExpiryDate(cardNumber, cvv, expiryDate).isPresent())) {
            log.debug("CardDao: findCard() card not found | invalid card info");
            throw new CardNotFoundException("invalid card :");
        }
    }

    @Override
    public void deleteCard(ValidateCardInteractor card) {
        log.info("DELETING Card from DB with Jpa Repository");
        if (!(cardJpaRepository.deleteByCardNumber(card.getCardNumber()) > 0)) {
            log.debug("deleteCard() card not found | probably invalid card info");
            throw new RuntimeException("invalid card info");
        }
    }

    @Override
    public void updateCard(UpdateCardInteractor cardModel) {
        log.info("Updating Card in DB with Jpa Repository");
        if (!(cardJpaRepository.updateCardBalanceAndClientEmailAndClientNameByCvvAndCardNumber(cardModel.getBalance(), cardModel.getClientEmail(), cardModel.getClientName(), cardModel.getCvv(), cardModel.getCardNumber()) > 0)) {
            log.debug("updateCard() card not found | probably invalid card info");
            throw new RuntimeException("invalid card info");
        }
    }
}
