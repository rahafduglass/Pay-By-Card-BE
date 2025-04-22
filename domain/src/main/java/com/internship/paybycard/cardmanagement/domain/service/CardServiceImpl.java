package com.internship.paybycard.cardmanagement.domain.service;

import com.internship.paybycard.cardmanagement.domain.mapper.CreateCardMapperImpl;
import com.internship.paybycard.cardmanagement.domain.mapper.UpdateCardMapperImpl;
import com.internship.paybycard.cardmanagement.domain.util.CardUtils;
import com.internship.paybycard.core.dao.CardDao;
import com.internship.paybycard.core.exception.CardCreationException;
import com.internship.paybycard.core.interactor.CreateCardInteractor;
import com.internship.paybycard.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.cardmanagement.domain.model.RealCardModel;
import com.internship.paybycard.core.service.CardService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardDao cardDao;

    private final CreateCardMapperImpl createCardMapperImpl;
    private final UpdateCardMapperImpl updateCardMapper;

    @Override
    public boolean createCard(CreateCardInteractor card){
        RealCardModel cardModel= createCardMapperImpl.mapTo(card);
        cardModel.setExpiryDate(LocalDate.now().plusYears(2));
        cardModel.setCVV(CardUtils.generateCVV());
        cardModel.setCardNumber(UUID.randomUUID().toString());

        try {
            cardDao.saveCard(cardModel);
        }catch (Exception e) {
            throw new CardCreationException("persistence error: couldn't create card: " +e);
        }
        return true;
    }

    @Override
    public void updateCard(UpdateCardInteractor card) {
        cardDao.updateCard(updateCardMapper.mapTo(card));
    }

    @Override
    public void validateCard(ValidateCardInteractor card) {
        cardDao.validateCard(card.getCardNumber(),card.getCVV(),card.getExpiryDate());
    }

    @Override
    public void deleteCard(ValidateCardInteractor card) {
        validateCard(card);
        cardDao.deleteCard(card);
    }

}
