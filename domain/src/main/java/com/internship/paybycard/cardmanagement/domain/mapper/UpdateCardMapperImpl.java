package com.internship.paybycard.cardmanagement.domain.mapper;

import com.internship.paybycard.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.core.mapper.UpdateCardMapper;
import com.internship.paybycard.cardmanagement.domain.model.RealCardModel;


public class UpdateCardMapperImpl implements UpdateCardMapper {

    @Override
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
