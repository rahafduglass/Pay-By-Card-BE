package com.internship.paybycard.cardmanagement.core.mapper;

import com.internship.paybycard.cardmanagement.core.interactor.CreateCardInteractor;
import com.internship.paybycard.cardmanagement.core.model.CardDto;

public interface CreateCardMapper {
    CardDto mapTo(CreateCardInteractor createCardInteractor);
}
