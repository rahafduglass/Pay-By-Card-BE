package com.internship.paybycard.cardmanagement.persistence.dao;


import com.internship.paybycard.cardmanagement.core.dao.CardDao;
import com.internship.paybycard.cardmanagement.core.exception.CardCreationException;
import com.internship.paybycard.cardmanagement.core.exception.CardNotFoundException;
import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.cardmanagement.core.mapper.CardMapper;
import com.internship.paybycard.cardmanagement.core.model.CardDto;
import com.internship.paybycard.cardmanagement.persistence.entity.CardEntity;
import com.internship.paybycard.cardmanagement.persistence.jpa.CardJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;


@Slf4j
@Repository
@RequiredArgsConstructor
public class CardDaoImpl implements CardDao {
    // todo unit test (u may need spring data test to initialize h2 DB and spring data as well)
    private final CardJpaRepository cardJpaRepository;

    private final CardMapper<CardDto, CardEntity> cardEntityMapper;

    @Override
    public void saveCard(CardDto cardDto) {
        log.info("Saving Card in DB with Jpa Repository");
        if (cardJpaRepository.save(cardEntityMapper.mapTo(cardDto)).getId() == null) {
            log.debug("saveCard() card couldn't save ");
            throw new CardCreationException("couldn't save card");
        }
    }

    @Override
    public CardDto findCard(String cardNumber, String cvv, LocalDate expiryDate) {
        log.info("Finding Card in DB with Jpa Repository");
        Optional<CardEntity> card=cardJpaRepository.findByCardNumberAndCvvAndExpiryDate(cardNumber, cvv, expiryDate);
        if (!card.isPresent()) {
            log.debug("CardDao: findCard() card not found | invalid card info");
            throw new CardNotFoundException("invalid card :");
        }
        else return cardEntityMapper.reverseTo(card.get());
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
        if (!(cardJpaRepository.updateCardBalanceAndClientEmailAndClientNameByCvvAndCardNumberAndExpiryDate(cardModel.getBalance(), cardModel.getClientEmail(), cardModel.getClientName(), cardModel.getCvv(), cardModel.getCardNumber(),cardModel.getExpiryDate()) > 0)) {
            log.debug("updateCard() card not found | probably invalid card info");
            throw new RuntimeException("invalid card info");
        }
    }
}
