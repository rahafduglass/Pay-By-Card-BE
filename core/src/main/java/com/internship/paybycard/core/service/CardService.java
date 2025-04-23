package com.internship.paybycard.core.service;

import com.internship.paybycard.core.interactor.CreateCardInteractor;
import com.internship.paybycard.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.core.result.Result;

public interface CardService {
    Result createCard(CreateCardInteractor card);
    Result updateCard(UpdateCardInteractor card);
    Result validateCard(ValidateCardInteractor card);

    Result deleteCard(ValidateCardInteractor card);
}
