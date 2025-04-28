package com.internship.paybycard.cardmanagement.core.dao;


import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.cardmanagement.core.model.CardModel;


import java.time.LocalDate;

public interface CardDao {
    void saveCard(CardModel card);
    void updateCard(UpdateCardInteractor card);
    void findCard(String cardNumber, String cvv, LocalDate expiryDate);
    void deleteCard(ValidateCardInteractor card);
}
