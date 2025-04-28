package com.internship.paybycard.cardmanagement.domain.mapper;

import com.internship.paybycard.cardmanagement.core.interactor.CreateCardInteractor;
import com.internship.paybycard.cardmanagement.core.mapper.CreateCardMapper;
import com.internship.paybycard.cardmanagement.domain.model.RealCardModel;


public class CreateCardMapperImpl implements CreateCardMapper {

    @Override
    public RealCardModel mapTo(CreateCardInteractor createCardInteractor) {

        RealCardModel cardModel= new RealCardModel();
        cardModel.setClientEmail(createCardInteractor.getClientEmail());
        cardModel.setClientName(createCardInteractor.getClientName());
        cardModel.setBalance(createCardInteractor.getBalance());
        return cardModel;
    }


}
