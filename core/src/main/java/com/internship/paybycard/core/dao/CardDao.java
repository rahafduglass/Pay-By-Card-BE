package com.internship.paybycard.core.dao;


import com.internship.paybycard.core.model.CardModel;


import java.time.LocalDate;

public interface CardDao {
    void saveCard(CardModel card);
    boolean updateCard(CardModel card);
    boolean validateCard(String cardNumber, String cvv, LocalDate expiryDate);
}
