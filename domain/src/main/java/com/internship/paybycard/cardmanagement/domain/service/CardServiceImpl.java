package com.internship.paybycard.cardmanagement.domain.service;

import com.internship.paybycard.cardmanagement.domain.util.CardUtils;
import com.internship.paybycard.core.dao.CardDao;
import com.internship.paybycard.core.exception.CardCreationException;
import com.internship.paybycard.core.exception.CardNotFoundException;
import com.internship.paybycard.core.model.CardModel;
import com.internship.paybycard.core.model.RealCardModel;
import com.internship.paybycard.core.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService<RealCardModel> {

    @Qualifier("cardDaoImpl")
    private final CardDao cardDao;

    @Override
    public boolean createCard(RealCardModel cardModel){
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
    public void updateCard(RealCardModel cardModel) {
        if(!validateCard(cardModel)) throw new CardNotFoundException("persistence error: invalid card info" );
        cardDao.updateCard(cardModel);
    }

    @Override
    public boolean validateCard(RealCardModel cardModel) {
        return cardDao.validateCard(cardModel.getCardNumber(),cardModel.getCVV(),cardModel.getExpiryDate());
    }

}
