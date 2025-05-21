package com.internship.cardmanagement.domain.service;

import com.internship.paybycard.cardmanagement.core.dao.CardDao;
import com.internship.paybycard.cardmanagement.core.interactor.CreateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.cardmanagement.core.dto.CardDto;
import com.internship.paybycard.cardmanagement.core.dto.NullCardDto;
import com.internship.paybycard.cardmanagement.core.dto.RealCardDto;
import com.internship.paybycard.cardmanagement.core.result.ErrorCode;
import com.internship.paybycard.cardmanagement.core.result.Result;
import com.internship.paybycard.cardmanagement.core.result.Status;
import com.internship.paybycard.cardmanagement.domain.mapper.CreateCardMapperImpl;
import com.internship.paybycard.cardmanagement.domain.service.CardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CardServiceImplTest {

    @Mock
    private CardDao cardDao;

    @Mock
    private CreateCardMapperImpl createCardMapper;

    @InjectMocks
    private CardServiceImpl cardService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void givenValidCardInteractorWithCardMapperMappingToCardModelAndCardDaoReturnSavedModel_whenCallCreateCard_thenShouldReturnGeneratedValuesWithAccepted() {
        CreateCardInteractor card= mock(CreateCardInteractor.class);
        when(card.getClientName()).thenReturn("someone");

        RealCardDto cardModel = new RealCardDto();
        when(createCardMapper.mapTo(card)).thenReturn(cardModel);
        when(cardDao.saveCard(cardModel)).thenReturn(cardModel);

        Result result = cardService.createCard(card);

        assertEquals(LocalDate.now().plusYears(2),cardModel.getExpiryDate());
        assertEquals(3,cardModel.getCVV().length());
        assertNotNull(cardModel.getCardNumber());
        assertEquals(Status.ACP,result.status());
        assertNull(result.errorCode());

    }

    @Test
    void createCard_success() {
        CreateCardInteractor interactor = mock(CreateCardInteractor.class);
        when(interactor.getClientName()).thenReturn("John Doe");
        when(interactor.getClientEmail()).thenReturn("john@example.com");

        RealCardDto realCard = new RealCardDto();
        when(createCardMapper.mapTo(interactor)).thenReturn(realCard);
        when(cardDao.saveCard(realCard)).thenReturn(realCard);

        Result<Long> result = cardService.createCard(interactor);

        assertEquals(Status.ACP, result.status());
        verify(cardDao).saveCard(realCard);
    }

    @Test
    void createCard_failedToSave() {
        CreateCardInteractor interactor = mock(CreateCardInteractor.class);
        when(interactor.getClientName()).thenReturn("Jane Doe");
        when(interactor.getClientEmail()).thenReturn("jane@example.com");

        RealCardDto realCard = new RealCardDto();
        when(createCardMapper.mapTo(interactor)).thenReturn(realCard);
        when(cardDao.saveCard(realCard)).thenReturn(new NullCardDto());

        Result<Long> result = cardService.createCard(interactor);

        assertEquals(Status.RJC, result.status());
        assertEquals(ErrorCode.FAILED, result.errorCode());
    }

    @Test
    void updateCard_success() {
        UpdateCardInteractor interactor = mock(UpdateCardInteractor.class);
        when(interactor.getCardNumber()).thenReturn("1234");

        when(cardDao.updateCardInfo(interactor)).thenReturn(1);

        Result<Void> result = cardService.updateCard(interactor);

        assertEquals(Status.ACP, result.status());
    }

    @Test
    void updateCard_failed() {
        UpdateCardInteractor interactor = mock(UpdateCardInteractor.class);
        when(interactor.getCardNumber()).thenReturn("1234");

        when(cardDao.updateCardInfo(interactor)).thenReturn(0);

        Result<Void> result = cardService.updateCard(interactor);

        assertEquals(Status.RJC, result.status());
        assertEquals(ErrorCode.INVALID_CARD_INFO, result.errorCode());
    }

    @Test
    void validateCard_success() {
        ValidateCardInteractor interactor = mock(ValidateCardInteractor.class);
        when(interactor.getCardNumber()).thenReturn("1234");
        when(interactor.getCVV()).thenReturn("456");
        LocalDate expiryDate = LocalDate.of(2030, 1, 1);
        when(interactor.getExpiryDate()).thenReturn(expiryDate);

        CardDto cardDto = mock(CardDto.class);

        when(cardDao.findCard("1234", "456", expiryDate)).thenReturn(cardDto);

        Result<CardDto> result = cardService.validateCard(interactor);

        assertEquals(Status.ACP, result.status());
        assertEquals(cardDto, result.data());
    }

    @Test
    void validateCard_invalid() {
        ValidateCardInteractor interactor = mock(ValidateCardInteractor.class);
        when(interactor.getCardNumber()).thenReturn("9999");
        when(interactor.getCVV()).thenReturn("111");
        LocalDate expiryDate = LocalDate.of(2025, 1, 1);
        when(interactor.getExpiryDate()).thenReturn(expiryDate);

        when(cardDao.findCard("9999", "111", expiryDate)).thenReturn(new NullCardDto());

        Result<CardDto> result = cardService.validateCard(interactor);

        assertEquals(Status.RJC, result.status());
        assertEquals(ErrorCode.INVALID_CARD_INFO, result.errorCode());
    }

    @Test
    void deleteCard_success() {
        ValidateCardInteractor interactor = mock(ValidateCardInteractor.class);
        when(interactor.getCardNumber()).thenReturn("5555");

        when(cardDao.deleteCard(interactor)).thenReturn(1);

        Result<Void> result = cardService.deleteCard(interactor);

        assertEquals(Status.ACP, result.status());
    }

    @Test
    void deleteCard_failed() {
        ValidateCardInteractor interactor = mock(ValidateCardInteractor.class);
        when(interactor.getCardNumber()).thenReturn("0000");

        when(cardDao.deleteCard(interactor)).thenReturn(0);

        Result<Void> result = cardService.deleteCard(interactor);

        assertEquals(Status.RJC, result.status());
        assertEquals(ErrorCode.INVALID_CARD_INFO, result.errorCode());
    }

}
