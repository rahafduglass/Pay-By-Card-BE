package com.internship.paybycard.core.dao;


import java.time.LocalDate;

public interface CardDao <M>{
    void saveCard(M card);
    boolean updateCard(M card);
    boolean validateCard(String cardNumber, String cvv, LocalDate expiryDate);
}
