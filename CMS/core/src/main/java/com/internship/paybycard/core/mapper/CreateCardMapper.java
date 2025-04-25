package com.internship.paybycard.core.mapper;

import com.internship.paybycard.core.interactor.CreateCardInteractor;
import com.internship.paybycard.core.model.CardModel;

public interface CreateCardMapper {
    CardModel mapTo(CreateCardInteractor createCardInteractor);
}
