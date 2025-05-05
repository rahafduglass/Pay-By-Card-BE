package com.internship.paybycard.cardmanagement.domain.service;

import com.internship.paybycard.cardmanagement.domain.mapper.CreateCardMapperImpl;
import com.internship.paybycard.cardmanagement.domain.model.RealCardModel;
import com.internship.paybycard.cardmanagement.core.result.Result;
import com.internship.paybycard.cardmanagement.domain.util.CardUtils;
import com.internship.paybycard.cardmanagement.core.dao.CardDao;
import com.internship.paybycard.cardmanagement.core.interactor.CreateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.cardmanagement.core.result.ErrorCode;
import com.internship.paybycard.cardmanagement.core.result.Status;
import com.internship.paybycard.cardmanagement.core.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardDao cardDao;

    private final CreateCardMapperImpl createCardMapperImpl;

    @Override
    public Result createCard(CreateCardInteractor card) {
        log.info("Creating new card {}", card.getClientName());

        log.debug("Mapping card interactor to card model");
        RealCardModel cardModel = createCardMapperImpl.mapTo(card);
        log.debug("the mapped card model: {}", cardModel);

        log.debug("setting generated values in card model");
        cardModel.setExpiryDate(LocalDate.now().plusYears(2));
        cardModel.setCVV(CardUtils.generateCVV());
        cardModel.setCardNumber(UUID.randomUUID().toString());
        log.debug("the card model with generated values: {}", cardModel);
        try {
            log.info("saving new card model by card dao");
            cardDao.saveCard(cardModel);
        } catch (Exception e) {
            log.error("couldn't create card: {}", e.getMessage());
            return new Result(Status.RJC, ErrorCode.FAILED);
        }
        log.debug("card: {} created successfully", cardModel.getCardNumber());
        return new Result(Status.ACP, null);
    }

    @Override
    public Result updateCard(UpdateCardInteractor card) {
        log.info(" Updating card");
        try {
            log.debug(" updating card by card dao");
            cardDao.updateCardInfo(card);
        } catch (Exception e) {
            log.error(" couldn't update card: {}", e.getMessage());
            return new Result(Status.RJC, ErrorCode.INVALID_CARD_INFO);
        }
        log.debug(" card updated successfully");
        return new Result(Status.ACP, null);
    }

    @Override
    public Result validateCard(ValidateCardInteractor card) {
        log.info(" Validating card");
        try {
            log.debug(" validating card by card dao");
            cardDao.findCard(card.getCardNumber(), card.getCVV(), card.getExpiryDate());
        } catch (Exception e) {
            log.debug(" invalid card: {}", e.getMessage());
            return new Result(Status.RJC, ErrorCode.INVALID_CARD_INFO);
        }
        log.debug(" card validated successfully");
        return new Result(Status.ACP, null);

    }

    @Override
    public Result deleteCard(ValidateCardInteractor card) {
        try {
            cardDao.deleteCard(card);
        } catch (Exception e) {
            log.error(" couldn't delete card: {}", e.getMessage());
            return new Result(Status.RJC, ErrorCode.INVALID_CARD_INFO);
        }
        log.debug(" card deleted successfully");
        return new Result(Status.ACP, null);
    }

}
