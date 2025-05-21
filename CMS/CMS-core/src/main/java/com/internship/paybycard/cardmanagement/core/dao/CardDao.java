package com.internship.paybycard.cardmanagement.core.dao;


import com.internship.paybycard.cardmanagement.core.SortDirection;
import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.cardmanagement.core.dto.CardDto;


import java.math.BigDecimal;
import java.time.LocalDate;

public interface CardDao {
    CardDto saveCard(CardDto card);
    int updateCardInfo(UpdateCardInteractor card);
    CardDto findCard(String cardNumber, String cvv, LocalDate expiryDate);
    CardDto findCardById(Long cardId);
    int deleteCard(ValidateCardInteractor card);
    int updateCardBalance(String cardNumber, String cvv, LocalDate expiryDate, BigDecimal newBalance);
    PageDetails findCards(int page, int size, SortDirection sortDirection);
}
