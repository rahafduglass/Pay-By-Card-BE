package com.internship.paybycard.cardmanagement.core.mapper;

import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.core.model.CardModel;

public interface UpdateCardMapper {
    CardModel mapTo(UpdateCardInteractor updateCardInteractor);
}
