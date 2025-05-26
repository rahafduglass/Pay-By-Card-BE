package com.internship.paybycard.cardmanagement.domain.service;

import com.internship.paybycard.cardmanagement.core.SortDirection;
import com.internship.paybycard.cardmanagement.core.dao.CardDao;
import com.internship.paybycard.cardmanagement.core.dao.PageDetails;
import com.internship.paybycard.cardmanagement.core.dto.CardDto;
import com.internship.paybycard.cardmanagement.core.dto.RealCardDto;
import com.internship.paybycard.cardmanagement.core.interactor.CreateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.WithdrawInteractor;
import com.internship.paybycard.cardmanagement.core.result.ErrorCode;
import com.internship.paybycard.cardmanagement.core.result.Result;
import com.internship.paybycard.cardmanagement.core.result.Status;
import com.internship.paybycard.cardmanagement.core.service.CardService;
import com.internship.paybycard.cardmanagement.domain.mapper.CreateCardMapperImpl;
import com.internship.paybycard.cardmanagement.domain.util.CardUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardDao cardDao;

    private final CreateCardMapperImpl createCardMapperImpl;

    @Override
    public Result<Long> createCard(CreateCardInteractor card) {
        try {
            log.info("Creating new card {}", card.getClientName());
            log.debug("Mapping card interactor to card model");
            RealCardDto cardModel = createCardMapperImpl.mapTo(card);
            log.debug("the mapped card model: {}", cardModel);

            log.debug("setting generated values in card model");
            cardModel.setExpiryDate(LocalDate.now().plusYears(2));
            cardModel.setCVV(CardUtils.generateCVV());
            cardModel.setCardNumber(UUID.randomUUID().toString());
            log.debug("the card model with generated values: {}", cardModel);

            log.info("saving new card model by card dao");
            CardDto savedCard = cardDao.saveCard(cardModel);
            if (savedCard.isNull()) {
                log.error("couldn't create card: {}", cardModel.getId());
                return new Result<>(Status.RJC, ErrorCode.FAILED, null);
            }
            log.debug("card created successfully");
            return new Result<>(Status.ACP, null, savedCard.getId());
        } catch (IllegalArgumentException e) {
            log.error("couldn't create card: {}", card.getClientEmail());
            return new Result<>(Status.RJC, ErrorCode.INVALID_INTERACTOR, null);
        } catch (Exception e) {
            log.error("couldn't create card: {}", card.getClientEmail());
            return new Result<>(Status.RJC, ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public Result<Void> updateCard(UpdateCardInteractor card) {
        try {
            log.info("Updating card");

            log.debug("updating card by card dao");
            if (cardDao.updateCardInfo(card) <= 0) {
                log.error("couldn't update card: {}", card.getCardNumber());
                return new Result<>(Status.RJC, ErrorCode.INVALID_CARD_INFO, null);
            }
            log.debug("card updated successfully");
            return new Result<>(Status.ACP, null, null);
        } catch (IllegalArgumentException e) {
            log.error("couldn't create card: invalid interactor{}", card);
            return new Result<>(Status.RJC, ErrorCode.INVALID_INTERACTOR, null);
        } catch (Exception e) {
            return new Result<>(Status.RJC, ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public Result<CardDto> validateCard(ValidateCardInteractor card) {
        try {
            log.info("Validating card");
            log.debug("validating card by card dao");
            CardDto cardDto = cardDao.findCard(card.getCardNumber(), card.getCVV(), card.getExpiryDate());
            if (cardDto.isNull()) {
                log.debug("invalid card: {}", card.getCardNumber());
                return new Result<>(Status.RJC, ErrorCode.INVALID_CARD_INFO, null);
            }
            log.debug("card validated successfully");
            return new Result<>(Status.ACP, null, cardDto);
        } catch (IllegalArgumentException e) {
            log.error("couldn't create card: invalid interactor{}", card);
            return new Result<>(Status.RJC, ErrorCode.INVALID_INTERACTOR, null);
        } catch (Exception e) {
            return new Result<>(Status.RJC, ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public Result<Void> deleteCard(ValidateCardInteractor card) {
        try {
            log.info("Deleting card");
            if (cardDao.deleteCard(card) <= 0) {
                log.error("couldn't delete card: {}", card.getCardNumber());
                return new Result<>(Status.RJC, ErrorCode.INVALID_CARD_INFO, null);
            }
            log.debug("card deleted successfully");
            return new Result<>(Status.ACP, null, null);
        } catch (IllegalArgumentException e) {
            log.error("couldn't create card: invalid interactor {}", card);
            return new Result<>(Status.RJC, ErrorCode.INVALID_INTERACTOR, null);
        } catch (Exception e) {
            return new Result<>(Status.RJC, ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public Result<Void> withdraw(WithdrawInteractor withdrawInteractor) {
        try {
            log.info("Withdrawing from card");
            ValidateCardInteractor card = withdrawInteractor.getValidateCardInfo();
            CardDto retrievedCard = cardDao.findCard(card.getCardNumber(), card.getCVV(), card.getExpiryDate());
            BigDecimal newBalance = retrievedCard.getBalance().subtract(withdrawInteractor.getAmount());
            if (!retrievedCard.isNull() && retrievedCard.getBalance().compareTo(withdrawInteractor.getAmount()) < 0) {
                return new Result<>(Status.RJC, ErrorCode.INSUFFICIENT_CARD_BALANCE, null);
            }
            if (cardDao.updateCardBalance(card.getCardNumber(), card.getCVV(), card.getExpiryDate(), newBalance) == 1)
                return new Result<>(Status.ACP, null, null);
            return new Result<>(Status.RJC, ErrorCode.INVALID_CARD_INFO, null);
        } catch (Exception e) {
            return new Result<>(Status.RJC, ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public Result<CardDto> getCard(Long cardId) {
        try {
            log.info("Retrieving card");
            CardDto retrievedCard = cardDao.findCardById(cardId);
            if (retrievedCard.isNull()) {
                log.debug("invalid card id {}", cardId);
                return new Result<>(Status.RJC, ErrorCode.INVALID_CARD_INFO, null);
            }
            log.debug("card retrieved successfully");
            return new Result<>(Status.ACP, null, retrievedCard);

        } catch (Exception e) {
            return new Result<>(Status.RJC, ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public Result<PageDetails> getAllCards(int page, int size, SortDirection sortDirection) {
        try {
            log.info("Retrieving cards with page size {}", size);
            PageDetails pageDetails = cardDao.findCards(page, size, sortDirection);
            if (pageDetails.isEmpty()) {
                log.debug("no records to retrieve in page= {} with size= {} ", page, size);
                return new Result<>(Status.RJC, ErrorCode.INVALID_PAGE_NUMBER, null);
            }
            log.debug("cards retrieved successfully");
            return new Result<>(Status.ACP, null, pageDetails);
        } catch (Exception e) {
            log.error("couldn't retrieve all cards: ", e);
            return new Result<>(Status.RJC, ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }
}