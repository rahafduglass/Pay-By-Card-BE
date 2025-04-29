package com.internship.cardmanagement.domain.service;

import com.internship.paybycard.cardmanagement.core.dao.CardDao;
import com.internship.paybycard.cardmanagement.core.exception.CardNotFoundException;
import com.internship.paybycard.cardmanagement.core.interactor.CreateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.cardmanagement.core.interactor.ValidateCardInteractor;
import com.internship.paybycard.cardmanagement.core.result.ErrorCode;
import com.internship.paybycard.cardmanagement.core.result.Result;
import com.internship.paybycard.cardmanagement.core.result.Status;
import com.internship.paybycard.cardmanagement.domain.mapper.CreateCardMapperImpl;
import com.internship.paybycard.cardmanagement.domain.model.RealCardModel;
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
    void testSuccessfulCardCreation() {
        CreateCardInteractor card= mock(CreateCardInteractor.class);
        when(card.getClientName()).thenReturn("someone");

        RealCardModel cardModel = new RealCardModel();
        when(createCardMapper.mapTo(card)).thenReturn(cardModel);
        doNothing().when(cardDao).saveCard(cardModel);

        Result result = cardService.createCard(card);

        assertEquals(LocalDate.now().plusYears(2),cardModel.getExpiryDate());
        assertEquals(3,cardModel.getCVV().length());
        assertNotNull(cardModel.getCardNumber());
        assertEquals(Status.ACP,result.status());
        assertNull(result.errorCode());

    }

    @Test
    void testFailedCardCreation() {
        CreateCardInteractor card= mock(CreateCardInteractor.class);
        when(card.getClientName()).thenReturn("someone");

        RealCardModel cardModel = new RealCardModel();
        when(createCardMapper.mapTo(card)).thenReturn(cardModel);
        doThrow(new RuntimeException("failed")).when(cardDao).saveCard(cardModel);

        Result result = cardService.createCard(card);

        assertEquals(LocalDate.now().plusYears(2),cardModel.getExpiryDate());
        assertEquals(3,cardModel.getCVV().length());
        assertNotNull(cardModel.getCardNumber());
        assertEquals(Status.RJC,result.status());
        assertEquals(ErrorCode.FAILED,result.errorCode());
    }

    @Test
    void testSuccessfulCardUpdate() {
        UpdateCardInteractor card= mock(UpdateCardInteractor.class);
        doNothing().when(cardDao).updateCard(card);

        Result result = cardService.updateCard(card);

        assertEquals(Status.ACP,result.status());
        assertNull(result.errorCode());
    }

    @Test
    void testFailedCardUpdate() {
        UpdateCardInteractor card= mock(UpdateCardInteractor.class);
        doThrow(new RuntimeException("failed")).when(cardDao).updateCard(card);

        Result result = cardService.updateCard(card);

        assertEquals(Status.RJC,result.status());
        assertEquals(ErrorCode.INVALID_CARD_INFO,result.errorCode());
    }

    @Test
    void testSuccessfulCardValidation(){
        ValidateCardInteractor card= mock(ValidateCardInteractor.class);

        doNothing().when(cardDao).findCard(anyString(),anyString(),any());

        Result result = cardService.validateCard(card);

        assertEquals(Status.ACP,result.status());
        assertNull(result.errorCode());
    }

    @Test
    void testFailedCardValidation(){
        ValidateCardInteractor card= mock(ValidateCardInteractor.class);
        when(card.getCardNumber()).thenReturn("someone");
        when(card.getExpiryDate()).thenReturn(LocalDate.now().plusYears(2));
        when(card.getCVV()).thenReturn("123");
        doThrow(new CardNotFoundException("failed")).when(cardDao).findCard(anyString(),anyString(),any());

        Result result = cardService.validateCard(card);

        assertEquals(Status.RJC,result.status());
        assertEquals(ErrorCode.INVALID_CARD_INFO,result.errorCode());
    }

    @Test
    void testSuccessfulCardDelete(){
        ValidateCardInteractor card= mock(ValidateCardInteractor.class);
        doNothing().when(cardDao).deleteCard(card);
        Result result = cardService.deleteCard(card);
        assertEquals(Status.ACP,result.status());
        assertNull(result.errorCode());
    }

    @Test
    void testFailedCardDelete(){
        ValidateCardInteractor card= mock(ValidateCardInteractor.class);
        doThrow(new RuntimeException("failed")).when(cardDao).deleteCard(card);
        Result result = cardService.deleteCard(card);
        assertEquals(Status.RJC,result.status());
        assertEquals(ErrorCode.INVALID_CARD_INFO,result.errorCode());
    }
}
