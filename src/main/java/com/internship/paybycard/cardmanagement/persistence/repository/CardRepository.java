package com.internship.paybycard.cardmanagement.persistence.repository;

import com.internship.paybycard.cardmanagement.domain.model.CardModel;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository {
    CardModel saveCard(CardModel cardModel);
}
