package com.internship.paybycard.cardmanagement.domain.repository;

import com.internship.paybycard.cardmanagement.domain.model.CardModel;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository {
    void saveCard(CardModel cardModel);
}
