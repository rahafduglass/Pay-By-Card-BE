package com.internship.paybycard.cardmanagement.persistence.adapter;

import com.internship.paybycard.cardmanagement.domain.model.CardModel;
import com.internship.paybycard.cardmanagement.domain.repository.CardRepository;
import com.internship.paybycard.cardmanagement.mapper.CardEntityMapper;
import com.internship.paybycard.cardmanagement.persistence.jpa.CardJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;



@Repository
@RequiredArgsConstructor
public class CardAdapter implements CardRepository {

    private final CardJpaRepository cardJpaRepository;
    private final CardEntityMapper cardEntityMapper;

    @Override
    public CardModel saveCard(CardModel cardModel) {
        return cardEntityMapper.entityToModel(cardJpaRepository.save(cardEntityMapper.modelToEntity(cardModel)));

    }
}
