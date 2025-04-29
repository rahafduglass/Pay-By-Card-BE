package com.internship.paybycard.cardmanagement.core.mapper;

import com.internship.paybycard.cardmanagement.core.interactor.CreateCardInteractor;
import com.internship.paybycard.cardmanagement.core.model.CardModel;

public interface CreateCardMapper {
    CardModel mapTo(CreateCardInteractor createCardInteractor);
}
