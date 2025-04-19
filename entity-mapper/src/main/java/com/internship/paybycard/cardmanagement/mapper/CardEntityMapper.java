package com.internship.paybycard.cardmanagement.mapper;

import com.internship.paybycard.cardmanagement.domain.model.CardModel;
import com.internship.paybycard.cardmanagement.entity.CardEntity;
import org.springframework.stereotype.Component;

@Component
public interface CardEntityMapper {
     CardEntity modelToEntity(CardModel cardModel);
     CardModel entityToModel(CardEntity cardEntity);
}
