package com.internship.paybycard.cardmanagement.core.service;

import com.internship.paybycard.cardmanagement.core.interactor.CreateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.WithdrawInteractor;
import com.internship.paybycard.cardmanagement.core.model.CardDto;
import com.internship.paybycard.cardmanagement.core.result.Result;

public interface CardService {
    Result<Long> createCard(CreateCardInteractor card);

    Result<Void> updateCard(UpdateCardInteractor card);

    Result<CardDto> validateCard(ValidateCardInteractor card);

    Result<Void> deleteCard(ValidateCardInteractor card);

    Result<Void> withdraw( WithdrawInteractor withdrawInteractor);

    Result<CardDto> getCard( Long cardId);
}
