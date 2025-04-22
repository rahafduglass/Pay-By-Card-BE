package com.internship.paybycard.core.mapper;

import com.internship.paybycard.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.core.model.CardModel;

public interface UpdateCardMapper {
    CardModel mapTo(UpdateCardInteractor updateCardInteractor);
}
