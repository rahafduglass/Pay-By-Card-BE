package com.internship.paybycard.core.service;

import com.internship.paybycard.core.interactor.CreateCardInteractor;
import com.internship.paybycard.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.core.interactor.ValidateCardInteractor;

public interface CardService {
    boolean createCard(CreateCardInteractor card);
    void updateCard(UpdateCardInteractor card);
    void validateCard(ValidateCardInteractor card);

    void deleteCard(ValidateCardInteractor card);
}
