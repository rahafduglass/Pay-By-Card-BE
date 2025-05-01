package com.internship.paybycard.cardmanagement.persistence.dao;


import com.internship.paybycard.cardmanagement.core.dao.CardDao;
import com.internship.paybycard.cardmanagement.core.exception.CardNotFoundException;
import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.cardmanagement.core.mapper.CardMapper;
import com.internship.paybycard.cardmanagement.core.model.CardModel;
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

    private final CardMapper<CardModel, CardEntity> cardEntityMapper;

    @Override
    public CardModel saveCard(CardModel cardModel) {
        log.info("Saving Card in DB with Jpa Repository");
        CardEntity retrievedEntity = cardJpaRepository.save(cardEntityMapper.mapTo(cardModel));
        return cardEntityMapper.reverseTo(retrievedEntity);
    }

    @Override
    public CardModel findCard(String cardNumber, String cvv, LocalDate expiryDate) {
        log.info("Finding Card in DB with Jpa Repository");
        Optional<CardEntity> retrievedEntity = cardJpaRepository.findByCardNumberAndCvvAndExpiryDate(cardNumber, cvv, expiryDate);
        if (retrievedEntity.isEmpty()) {
            log.debug("CardDao: findCard() card not found | invalid card info");
            throw new CardNotFoundException("invalid card :");
        } else {
            CardEntity cardEntity = retrievedEntity.get();
            return cardEntityMapper.reverseTo(cardEntity);
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
    public void updateCardBalanceAndClientEmailAndClientNameByCvvAndCardNumberAndExpiryDate(UpdateCardInteractor cardModel) {
        log.info("Updating Card in DB with Jpa Repository");
        if (!(cardJpaRepository.updateCardBalanceAndClientEmailAndClientNameByCvvAndCardNumberAndExpiryDate(cardModel.getBalance(), cardModel.getClientEmail(), cardModel.getClientName(), cardModel.getCvv(), cardModel.getCardNumber(),cardModel.getExpiryDate()) > 0)) {
            log.debug("updateCard() card not found | probably invalid card info");
            throw new RuntimeException("invalid card info");
        }
    }

    @Override
    public CardModel findCardById(Long id) {
        Optional<CardEntity> retrievedEntity = cardJpaRepository.findById(id);
        if (retrievedEntity.isPresent())
            return cardEntityMapper.reverseTo(retrievedEntity.get());
        else throw new CardNotFoundException("couldn't find card with id");
    }
}
