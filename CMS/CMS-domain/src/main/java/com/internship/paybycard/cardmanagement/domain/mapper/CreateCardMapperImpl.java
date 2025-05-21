package com.internship.paybycard.cardmanagement.domain.mapper;

import com.internship.paybycard.cardmanagement.core.interactor.CreateCardInteractor;
import com.internship.paybycard.cardmanagement.core.mapper.CreateCardMapper;
import com.internship.paybycard.cardmanagement.core.dto.RealCardDto;


public class CreateCardMapperImpl implements CreateCardMapper {

    @Override
    public RealCardDto mapTo(CreateCardInteractor createCardInteractor) {
        if(createCardInteractor == null) {
            throw new IllegalArgumentException("createCardInteractor cannot be null");
        }

        RealCardDto cardModel= new RealCardDto();
        cardModel.setClientEmail(createCardInteractor.getClientEmail());
        cardModel.setClientName(createCardInteractor.getClientName());
        cardModel.setBalance(createCardInteractor.getBalance());
        return cardModel;
    }


}
