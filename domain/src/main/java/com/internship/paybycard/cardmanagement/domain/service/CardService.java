package com.internship.paybycard.cardmanagement.domain.service;

import com.internship.paybycard.cardmanagement.domain.model.CardModel;
import com.internship.paybycard.cardmanagement.domain.util.CardUtils;
import com.internship.paybycard.cardmanagement.domain.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public boolean createCard(CardModel cardModel) {
        cardModel.setExpiryDate(LocalDate.now().plusYears(2));
        cardModel.setCVV(CardUtils.generateCVV());
        cardModel.setCardNumber("temp");
        CardModel savedCard= cardRepository.saveCard(cardModel);

        savedCard.setCardNumber(CardUtils.generateCardNumber(savedCard.getId()));
        cardRepository.saveCard(savedCard);
        return true;
    }
}
