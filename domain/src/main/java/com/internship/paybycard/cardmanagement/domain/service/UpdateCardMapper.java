package com.internship.paybycard.cardmanagement.domain.service;

import com.internship.paybycard.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.core.model.RealCardModel;
import org.springframework.stereotype.Component;

@Component
public class UpdateCardMapper {

    public RealCardModel mapTo(UpdateCardInteractor updateCardInteractor) {

        RealCardModel cardModel = new RealCardModel();

        cardModel.setExpiryDate(updateCardInteractor.getExpiryDate());
        cardModel.setCardNumber(updateCardInteractor.getCardNumber());
        cardModel.setCVV(updateCardInteractor.getCvv());
        cardModel.setClientEmail(updateCardInteractor.getClientEmail());
        cardModel.setClientName(updateCardInteractor.getClientName());
        cardModel.setBalance(updateCardInteractor.getBalance());
        return cardModel;
    }

}
