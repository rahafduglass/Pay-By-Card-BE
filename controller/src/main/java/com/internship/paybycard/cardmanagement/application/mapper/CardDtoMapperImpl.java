package com.internship.paybycard.cardmanagement.application.mapper;

import com.internship.paybycard.cardmanagement.application.interactors.CardDto;
import com.internship.paybycard.cardmanagement.application.interactors.CreateCardRequest;
import com.internship.paybycard.cardmanagement.application.interactors.NullCardDto;
import com.internship.paybycard.cardmanagement.application.interactors.UpdateCardRequest;
import com.internship.paybycard.core.mapper.CardMapper;
import com.internship.paybycard.core.model.CardModel;
import com.internship.paybycard.core.model.NullCardModel;
import com.internship.paybycard.core.model.RealCardModel;
import org.springframework.stereotype.Component;

@Component
public class CardDtoMapperImpl implements CardMapper<CardModel, CardDto> {

    @Override
    public CardModel mapTo(CardDto cardDto) {

        if((cardDto instanceof CreateCardRequest createCardRequest)) { // this sentence checks AND casts as well if its right type
            return cardRequestToModel(createCardRequest);
        }

        if((cardDto instanceof UpdateCardRequest cardRequest)) {
            return updateCardRequestToModel(cardRequest);
        }

        return new NullCardModel();
    }

    @Override
    public CardDto reverseTo(CardModel cardModel) {
        if(cardModel.isNull()) {
            return new NullCardDto();
        }
        CreateCardRequest createCardRequest = new CreateCardRequest();
        createCardRequest.setBalance(cardModel.getBalance());
        createCardRequest.setClientEmail(cardModel.getClientEmail());
        createCardRequest.setClientName(cardModel.getClientName());
        return createCardRequest;
    }


    private CardModel updateCardRequestToModel(UpdateCardRequest cardRequest) {
        RealCardModel cardModel = new RealCardModel();
        cardModel.setCardNumber(cardRequest.getCardNumber());
        cardModel.setCVV(cardRequest.getCvv());
        cardModel.setExpiryDate(cardRequest.getExpiryDate());
        cardModel.setBalance(cardRequest.getBalance());
        cardModel.setClientEmail(cardRequest.getClientEmail());
        cardModel.setClientName(cardRequest.getClientName());
        return cardModel;
    }

    private CardModel cardRequestToModel(CreateCardRequest createCardRequest) {
        RealCardModel cardModel = new RealCardModel();
        cardModel.setBalance(createCardRequest.getBalance());
        cardModel.setClientEmail(createCardRequest.getClientEmail());
        cardModel.setClientName(createCardRequest.getClientName());
        return cardModel;
    }


}
