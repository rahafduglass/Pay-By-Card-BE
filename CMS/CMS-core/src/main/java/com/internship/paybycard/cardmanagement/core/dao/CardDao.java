package com.internship.paybycard.cardmanagement.core.dao;


import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.cardmanagement.core.model.CardDto;


import java.time.LocalDate;

public interface CardDao {
    CardDto saveCard(CardDto card);
    void updateCardInfo(UpdateCardInteractor card);
    CardDto findCard(String cardNumber, String cvv, LocalDate expiryDate);
    CardDto findCardById(Long cardId);
    void deleteCard(ValidateCardInteractor card);
}
