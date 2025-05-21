package com.internship.paybycard.cardmanagement.core.service;

import com.internship.paybycard.cardmanagement.core.SortDirection;
import com.internship.paybycard.cardmanagement.core.dao.PageDetails;
import com.internship.paybycard.cardmanagement.core.interactor.CreateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.WithdrawInteractor;
import com.internship.paybycard.cardmanagement.core.dto.CardDto;
import com.internship.paybycard.cardmanagement.core.result.Result;

public interface CardService {
    Result<Long> createCard(CreateCardInteractor card);

    Result<Void> updateCard(UpdateCardInteractor card);

    Result<CardDto> validateCard(ValidateCardInteractor card);

    Result<Void> deleteCard(ValidateCardInteractor card);

    Result<Void> withdraw( WithdrawInteractor withdrawInteractor);

    Result<CardDto> getCard( Long cardId);

    Result<PageDetails> getAllCards(int page, int size, SortDirection sortDirection);
}
