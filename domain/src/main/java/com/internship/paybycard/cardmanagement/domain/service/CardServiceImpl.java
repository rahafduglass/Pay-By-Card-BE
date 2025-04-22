package com.internship.paybycard.cardmanagement.domain.service;

import com.internship.paybycard.cardmanagement.domain.util.CardUtils;
import com.internship.paybycard.core.dao.CardDao;
import com.internship.paybycard.core.exception.CardCreationException;
import com.internship.paybycard.core.interactor.CreateCardInteractor;
import com.internship.paybycard.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.core.model.RealCardModel;
import com.internship.paybycard.core.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    @Qualifier("cardDaoImpl")
    private final CardDao cardDao;

    private final CreateCardMapper createCardMapper;
    private final UpdateCardMapper updateCardMapper;

    @Override
    public boolean createCard(CreateCardInteractor card){
        RealCardModel cardModel= createCardMapper.mapTo(card);

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
    public boolean validateCard(ValidateCardInteractor card) {
        return cardDao.validateCard(card.getCardNumber(),card.getCVV(),card.getExpiryDate());
    }

}
