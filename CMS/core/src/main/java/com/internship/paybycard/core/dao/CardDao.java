package com.internship.paybycard.core.dao;


import com.internship.paybycard.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.core.model.CardModel;


import java.time.LocalDate;

public interface CardDao {
    void saveCard(CardModel card);
    void updateCard(UpdateCardInteractor card);
    void findCard(String cardNumber, String cvv, LocalDate expiryDate);
    void deleteCard(ValidateCardInteractor card);
}
