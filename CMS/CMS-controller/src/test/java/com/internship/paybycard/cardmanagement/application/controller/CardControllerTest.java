package com.internship.paybycard.cardmanagement.application.controller;

import com.internship.paybycard.cardmanagement.application.interactors.CreateCardRequest;
import com.internship.paybycard.cardmanagement.application.interactors.ValidateCardRequest;
import com.internship.paybycard.cardmanagement.core.model.CardDto;
import com.internship.paybycard.cardmanagement.core.model.NullCardDto;
import com.internship.paybycard.cardmanagement.core.model.RealCardDto;
import com.internship.paybycard.cardmanagement.core.result.ErrorCode;
import com.internship.paybycard.cardmanagement.core.result.Result;
import com.internship.paybycard.cardmanagement.core.result.Status;
import com.internship.paybycard.cardmanagement.core.service.CardService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CardControllerTest {

    @Mock
    private CardService cardService;

    @InjectMocks
    private CardController cardController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenValidRequestWithCardServiceReturnAccepted_whenCallCreateCard_thenShouldReturnCreatedHttpStatus() {
        CreateCardRequest request = new CreateCardRequest();
        when(cardService.createCard(request)).thenReturn(new Result(Status.ACP, null,null));
        ResponseEntity<Result<Void>> response = cardController.createCard(request);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(Status.ACP, response.getBody().status());
    }

    @Test
    public void givenInvalidRequestWithCardServiceReturnRejectedStatusAndInvalidCardInfoErrorCode_whenCallCreateCard_thenShouldReturnBadRequestHttpStatus() {
        CreateCardRequest request = new CreateCardRequest();
        when(cardService.createCard(request)).thenReturn(new Result(Status.RJC, ErrorCode.INVALID_CARD_INFO,new NullCardDto()));
        ResponseEntity<Result<Void>> response = cardController.createCard(request);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(ErrorCode.INVALID_CARD_INFO, response.getBody().errorCode());
    }

    @Test
    public void givenAnyValidRequestWithCardServiceReturnAccepted_whenCallUpdateCard_thenReturnNoContentHttpStatus() {

        when(cardService.updateCard(any())).thenReturn(new Result(Status.ACP, null,null));
        ResponseEntity<Result<Void>> response = cardController.updateCard(any());
        assertEquals(204, response.getStatusCodeValue());
        assertEquals(Status.ACP, response.getBody().status());

    }

    @Test
    public void givenAnyInvalidRequestWithCardServiceReturnRejectedStatusAndInvalidCardInfoErrorCode_whenCallUpdateCard_thenReturnBadRequestHttpStatus() {
        when(cardService.updateCard(any())).thenReturn(new Result(Status.RJC, ErrorCode.INVALID_CARD_INFO,null));
        ResponseEntity<Result<Void>> response = cardController.updateCard(any());
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(ErrorCode.INVALID_CARD_INFO, response.getBody().errorCode());

    }

    @Test
    public void givenAnyValidRequestWithCardServiceReturnAccepted_whenCallDeleteCard_thenReturnNoContentHttpStatus() {
        when(cardService.deleteCard(any())).thenReturn(new Result(Status.ACP, null,null));
        ResponseEntity<Result<Void>> response = cardController.deleteCard(any());
        assertEquals(204, response.getStatusCodeValue());
        assertEquals(Status.ACP, response.getBody().status());
    }

    @Test
    public void givenAnyInvalidRequestWithCardServiceReturnRejectedAndInvalidCardInfo_whenCallDeleteCard_thenReturnBadRequestHttpStatus() {
        ValidateCardRequest request = new ValidateCardRequest();
        when(cardService.deleteCard(request)).thenReturn(new Result(Status.RJC, ErrorCode.INVALID_CARD_INFO,null));
        ResponseEntity<Result<Void>> response = cardController.deleteCard(request);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(ErrorCode.INVALID_CARD_INFO, response.getBody().errorCode());
    }

    @Test
    //will break after merge
    public void givenAnyValidRequestWithCardServiceReturnAccepted_thenCallValidateCard_thenShouldReturnNoContentHttpRequest() {
        ValidateCardRequest request = new ValidateCardRequest();
        when(cardService.validateCard(request)).thenReturn(new Result(Status.ACP, null,new RealCardDto()));
        ResponseEntity<Result<CardDto>> response = cardController.validateCard(request);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Status.ACP, response.getBody().status());
        assertNotNull(response.getBody().data());
    }

    @Test
    public void givenAnyInvalidRequestWithCardServiceReturnRejectedAndInvalidCardInfo_whenCallValidateCard_thenShouldReturnBadRequestHttpStatus() {
        ValidateCardRequest request = new ValidateCardRequest();
        when(cardService.validateCard(request)).thenReturn(new Result(Status.RJC, ErrorCode.INVALID_CARD_INFO,null));
        ResponseEntity<Result<CardDto>> response = cardController.validateCard(request);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(ErrorCode.INVALID_CARD_INFO, response.getBody().errorCode());
    }
}
