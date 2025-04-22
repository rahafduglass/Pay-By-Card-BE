package com.internship.paybycard.cardmanagement.domain.service;

import com.internship.paybycard.core.interactor.CreateCardInteractor;
import com.internship.paybycard.core.model.RealCardModel;
import org.springframework.stereotype.Component;

@Component
public class CreateCardMapper {

    public RealCardModel mapTo(CreateCardInteractor createCardInteractor) {

        RealCardModel cardModel= new RealCardModel();
        cardModel.setClientEmail(createCardInteractor.getClientEmail());
        cardModel.setClientName(createCardInteractor.getClientName());
        cardModel.setBalance(createCardInteractor.getBalance());
        return cardModel;
    }

}
