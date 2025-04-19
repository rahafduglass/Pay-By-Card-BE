package com.internship.paybycard.cardmanagement.mapper;

import com.internship.paybycard.cardmanagement.domain.model.CardModel;
import com.internship.paybycard.cardmanagement.entity.CardEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface CardMapper {
    CardEntity modelToEntity(CardModel cardModel);

    CardModel entityToModel(CardEntity cardEntity);
}
