package com.internship.paybycard.cardmanagement.domain.mapper;

import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.core.mapper.UpdateCardMapper;
import com.internship.paybycard.cardmanagement.core.dto.RealCardDto;


public class UpdateCardMapperImpl implements UpdateCardMapper {

    @Override
    public RealCardDto mapTo(UpdateCardInteractor updateCardInteractor) {

        RealCardDto cardModel = new RealCardDto();

        cardModel.setExpiryDate(updateCardInteractor.getExpiryDate());
        cardModel.setCardNumber(updateCardInteractor.getCardNumber());
        cardModel.setCVV(updateCardInteractor.getCvv());
        cardModel.setClientEmail(updateCardInteractor.getClientEmail());
        cardModel.setClientName(updateCardInteractor.getClientName());
        cardModel.setBalance(updateCardInteractor.getBalance());
        return cardModel;
    }

}
