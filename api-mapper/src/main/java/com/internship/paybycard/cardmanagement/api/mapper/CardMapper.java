package com.internship.paybycard.cardmanagement.api.mapper;

import com.internship.paybycard.cardmanagement.api.dto.CardRequest;
import com.internship.paybycard.cardmanagement.domain.model.CardModel;
import org.springframework.stereotype.Component;

@Component
public interface CardMapper {
     CardModel requestToModel(CardRequest cardRequest);

}
