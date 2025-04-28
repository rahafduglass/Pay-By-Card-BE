package com.internship.paybycard.cardmanagement.core.service;

import com.internship.paybycard.cardmanagement.core.interactor.CreateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.cardmanagement.core.result.Result;

public interface CardService {
    Result createCard(CreateCardInteractor card);
    Result updateCard(UpdateCardInteractor card);
    Result validateCard(ValidateCardInteractor card);

    Result deleteCard(ValidateCardInteractor card);
}
