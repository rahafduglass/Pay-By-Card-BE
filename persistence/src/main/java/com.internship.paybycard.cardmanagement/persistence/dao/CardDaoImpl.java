package com.internship.paybycard.cardmanagement.persistence.dao;


import com.internship.paybycard.cardmanagement.persistence.entity.CardEntity;
import com.internship.paybycard.cardmanagement.persistence.jpa.CardJpaRepository;
import com.internship.paybycard.core.dao.CardDao;
import com.internship.paybycard.core.mapper.CardMapper;
import com.internship.paybycard.core.model.CardModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;



@Repository
@RequiredArgsConstructor
public class CardDaoImpl implements CardDao<CardModel> {

    private final CardJpaRepository cardJpaRepository;

    @Qualifier("cardEntityMapperImpl")
    private final CardMapper<CardModel,CardEntity> cardEntityMapper;

    @Override
    public void saveCard(CardModel cardModel) {
        cardEntityMapper.mapToModel(cardJpaRepository.save(cardEntityMapper.reverseMap(cardModel)));
    }
}
