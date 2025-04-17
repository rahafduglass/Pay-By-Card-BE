package com.internship.paybycard.cardmanagement.persistence.adapter;

import com.internship.paybycard.cardmanagement.domain.mapper.CardMapper;
import com.internship.paybycard.cardmanagement.domain.model.CardModel;
import com.internship.paybycard.cardmanagement.persistence.jpa.CardJpaRepository;
import com.internship.paybycard.cardmanagement.persistence.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;



@Repository
@RequiredArgsConstructor
public class CardAdapter implements CardRepository {

    private final CardJpaRepository cardJpaRepository;
    private final CardMapper cardMapper;

    @Override
    public CardModel saveCard(CardModel cardModel) {
        return cardMapper.entityToModel(cardJpaRepository.save(cardMapper.modelToEntity(cardModel)));

    }
}
