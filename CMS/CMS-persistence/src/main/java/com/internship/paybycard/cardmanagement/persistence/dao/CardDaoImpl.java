package com.internship.paybycard.cardmanagement.persistence.dao;


import com.internship.paybycard.cardmanagement.core.SortDirection;
import com.internship.paybycard.cardmanagement.core.dao.CardDao;
import com.internship.paybycard.cardmanagement.core.dao.PageDetails;
import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.cardmanagement.core.mapper.CardMapper;
import com.internship.paybycard.cardmanagement.core.dto.CardDto;
import com.internship.paybycard.cardmanagement.persistence.entity.CardEntity;
import com.internship.paybycard.cardmanagement.persistence.jpa.CardJpaRepository;
import com.internship.paybycard.cardmanagement.persistence.mapper.PageDetailsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;


@Slf4j
@Repository
@RequiredArgsConstructor
public class CardDaoImpl implements CardDao {

    private final CardJpaRepository cardJpaRepository;
    private final PageDetailsMapper pageDetailsMapper;
    private final CardMapper<CardDto, CardEntity> cardEntityMapper;

    @Override
    public CardDto saveCard(CardDto cardModel) {
        log.info("Saving Card in DB with Jpa Repository");
        return cardEntityMapper.reverseTo(cardJpaRepository.save(cardEntityMapper.mapTo(cardModel)));
    }

    @Override
    public CardDto findCard(String cardNumber, String cvv, LocalDate expiryDate) {
        log.info("Finding Card in DB with Jpa Repository, cardNumber: {}", cardNumber);
        Optional<CardEntity> retrievedEntity = cardJpaRepository.findByCardNumberAndCvvAndExpiryDate(cardNumber, cvv, expiryDate);
        return cardEntityMapper.reverseTo(retrievedEntity.orElse(null));
    }

    @Override
    public int deleteCard(ValidateCardInteractor card) {
        log.info("DELETING Card from DB with Jpa Repository {}", card.getCardNumber());
        return cardJpaRepository.deleteByCardNumber(card.getCardNumber());
    }

    @Override
    public int updateCardBalance(String cardNumber, String cvv, LocalDate expiryDate, BigDecimal newBalance) {
        log.info("Updating Card in DB with Jpa Repository, cardNumber: {}", cardNumber);
        return cardJpaRepository.updateCardBalance(cardNumber,cvv,expiryDate,newBalance);
    }

    @Override
    public PageDetails findCards(int page, int size, SortDirection sortDirection) {
        Sort.Direction direction= Sort.Direction.fromString(sortDirection.toString());
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction,"id"));
        Page<CardEntity> paginatedEntities= cardJpaRepository.findAll(pageable);
        return pageDetailsMapper.toPaginatedDtos(paginatedEntities);
    }

    @Override
    public int updateCardInfo(UpdateCardInteractor cardModel) {
        log.info("Updating Card in DB with Jpa Repository {}", cardModel.getCardNumber());
        return cardJpaRepository.updateCardBalanceAndClientEmailAndClientNameByCvvAndCardNumberAndExpiryDate(cardModel.getBalance(), cardModel.getClientEmail(), cardModel.getClientName(), cardModel.getCvv(), cardModel.getCardNumber(), cardModel.getExpiryDate());
    }

    @Override
    public CardDto findCardById(Long id) {
        Optional<CardEntity> retrievedEntity = cardJpaRepository.findById(id);
        return cardEntityMapper.reverseTo(retrievedEntity.orElse(null));
    }
}
