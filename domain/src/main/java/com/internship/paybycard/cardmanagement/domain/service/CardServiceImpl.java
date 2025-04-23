package com.internship.paybycard.cardmanagement.domain.service;

import com.internship.paybycard.cardmanagement.domain.mapper.CreateCardMapperImpl;
import com.internship.paybycard.cardmanagement.domain.model.RealCardModel;
import com.internship.paybycard.cardmanagement.domain.result.Result;
import com.internship.paybycard.cardmanagement.domain.util.CardUtils;
import com.internship.paybycard.core.dao.CardDao;
import com.internship.paybycard.core.interactor.CreateCardInteractor;
import com.internship.paybycard.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.core.logger.CardServiceLogger;
import com.internship.paybycard.core.result.ErrorCode;
import com.internship.paybycard.core.result.Status;
import com.internship.paybycard.core.service.CardService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardDao cardDao;

    private final CreateCardMapperImpl createCardMapperImpl;

    private final CardServiceLogger LOGGER;

    @Override
    public Result createCard(CreateCardInteractor card) {
        LOGGER.info("CardService: Creating new card");

        LOGGER.debug("CardService: Mapping card interactor to card model");
        RealCardModel cardModel = createCardMapperImpl.mapTo(card);
        LOGGER.debug("CardService: the mapped card model: " + cardModel);

        LOGGER.debug("CardService: setting generated values in card model");
        cardModel.setExpiryDate(LocalDate.now().plusYears(2));
        cardModel.setCVV(CardUtils.generateCVV());
        cardModel.setCardNumber(UUID.randomUUID().toString());
        LOGGER.debug("CardService: the card model with generated values: " + cardModel);
        try {
            LOGGER.info("CardService: saving new card model by card dao");
            cardDao.saveCard(cardModel);
        } catch (Exception e) {
            LOGGER.error("CardService: couldn't create card: " + e.getMessage());
            return new Result(Status.RJC, ErrorCode.FAILED);
        }
        LOGGER.debug("CardService: card created successfully");
        return new Result(Status.ACP, null);
    }

    @Override
    public Result updateCard(UpdateCardInteractor card) {
        LOGGER.info("CardService: Updating card");
        try {
            LOGGER.debug("CardService: updating card by card dao");
            cardDao.updateCard(card);
        } catch (Exception e) {
            LOGGER.error("CardService: couldn't update card: " + e.getMessage());
            return new Result(Status.RJC, ErrorCode.INVALID_CARD_INFO);
        }
        LOGGER.debug("CardService: card updated successfully");
        return new Result(Status.ACP, null);
    }

    @Override
    public Result validateCard(ValidateCardInteractor card) {
        LOGGER.info("CardService: Validating card");
        try {
            LOGGER.debug("CardService: validating card by card dao");
            cardDao.findCard(card.getCardNumber(), card.getCVV(), card.getExpiryDate());
        }
        catch (Exception e) {
            LOGGER.debug("CardService: invalid card: " + e.getMessage());
            return new Result(Status.RJC, ErrorCode.INVALID_CARD_INFO);
        }
        LOGGER.debug("CardService: card validated successfully");
        return new Result(Status.ACP, null);

    }

    @Override
    public Result deleteCard(ValidateCardInteractor card) {
        try {
            cardDao.deleteCard(card);
        }catch (Exception e) {
            LOGGER.error("CardService: couldn't delete card: " + e.getMessage());
            return new Result(Status.RJC, ErrorCode.INVALID_CARD_INFO);
        }
        LOGGER.debug("CardService: card deleted successfully");
        return new Result(Status.ACP, null);
    }

}
