package com.internship.paybycard.cardmanagement.core.mapper;

import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.core.model.CardDto;

public interface UpdateCardMapper {
    CardDto mapTo(UpdateCardInteractor updateCardInteractor);
}
