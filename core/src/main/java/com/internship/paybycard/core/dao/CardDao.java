package com.internship.paybycard.core.dao;


import com.internship.paybycard.core.model.RealCardModel;

import java.time.LocalDate;

public interface CardDao {
    void saveCard(RealCardModel card);
    boolean updateCard(RealCardModel card);
    boolean validateCard(String cardNumber, String cvv, LocalDate expiryDate);
}
