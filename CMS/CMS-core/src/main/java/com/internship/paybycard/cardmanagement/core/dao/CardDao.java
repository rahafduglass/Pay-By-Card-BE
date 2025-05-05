package com.internship.paybycard.cardmanagement.core.dao;


import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.cardmanagement.core.model.CardModel;


import java.time.LocalDate;

public interface CardDao {
    CardModel saveCard(CardModel card);
    void updateCardInfo(UpdateCardInteractor card);
    CardModel findCard(String cardNumber, String cvv, LocalDate expiryDate);
    CardModel findCardById(Long cardId);
    void deleteCard(ValidateCardInteractor card);
}
