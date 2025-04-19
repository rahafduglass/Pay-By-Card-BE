package com.internship.paybycard.cardmanagement.domain.service;

import com.internship.paybycard.cardmanagement.domain.dao.CardDao;
import com.internship.paybycard.cardmanagement.domain.exception.CardCreationException;
import com.internship.paybycard.cardmanagement.domain.model.CardModel;
import com.internship.paybycard.cardmanagement.domain.util.CardUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService{
    private final CardDao cardDao;

    public boolean createCard(CardModel cardModel){
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
}
