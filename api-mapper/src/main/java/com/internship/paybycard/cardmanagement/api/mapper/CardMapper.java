package com.internship.paybycard.cardmanagement.api.mapper;

import com.internship.paybycard.cardmanagement.api.dto.CardRequest;
import com.internship.paybycard.cardmanagement.domain.model.CardModel;
import com.internship.paybycard.cardmanagement.persistence.entity.CardEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface CardMapper {
    CardModel requestToModel(CardRequest cardRequest);

    CardEntity modelToEntity(CardModel cardModel);

    CardModel entityToModel(CardEntity cardEntity);
}
