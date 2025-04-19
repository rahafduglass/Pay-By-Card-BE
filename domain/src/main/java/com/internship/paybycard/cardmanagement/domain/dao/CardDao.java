package com.internship.paybycard.cardmanagement.domain.dao;

import com.internship.paybycard.cardmanagement.domain.model.CardModel;
import org.springframework.stereotype.Repository;

@Repository
public interface CardDao {
    void saveCard(CardModel cardModel);
}
