package com.internship.paybycard.cardmanagement.application.controller;

import com.internship.paybycard.cardmanagement.application.interactors.CreateCardRequest;
import com.internship.paybycard.cardmanagement.application.interactors.UpdateCardRequest;
import com.internship.paybycard.cardmanagement.application.interactors.ValidateCardRequest;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testSuccessful_cardCreation() {
        CreateCardRequest request = new CreateCardRequest();
        when(cardService.createCard(request)).thenReturn(new Result(Status.ACP,null));
        ResponseEntity<String> response = cardController.createCard(request);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(Status.ACP.name(), response.getBody());
    }


    @Test
    public void testInvalidCardInfo_cardCreation() {
        CreateCardRequest request = new CreateCardRequest();
        when(cardService.createCard(request)).thenReturn(new Result(Status.RJC, ErrorCode.INVALID_CARD_INFO));
        ResponseEntity<String> response = cardController.createCard(request);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(ErrorCode.INVALID_CARD_INFO.name(), response.getBody());
    }

    @Test
    public void testSuccessful_cardUpdate() {
        UpdateCardRequest request = new UpdateCardRequest();

        when(cardService.updateCard(request)).thenReturn(new Result(Status.ACP,null));
        ResponseEntity<String> response = cardController.updateCard(request);
        assertEquals(204, response.getStatusCodeValue());
        assertEquals(Status.ACP.name(), response.getBody());

    }

    @Test
    public void testInvalidCard_cardUpdate() {
        UpdateCardRequest request = new UpdateCardRequest();
        when(cardService.updateCard(request)).thenReturn(new Result(Status.RJC, ErrorCode.INVALID_CARD_INFO));
        ResponseEntity<String> response = cardController.updateCard(request);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(ErrorCode.INVALID_CARD_INFO.name(), response.getBody());
    }

    @Test
    public void testSuccessful_cardDeletion() {
        ValidateCardRequest request = new ValidateCardRequest();
        when(cardService.deleteCard(request)).thenReturn(new Result(Status.ACP,null));
        ResponseEntity<String> response = cardController.deleteCard(request);
        assertEquals(204, response.getStatusCodeValue());
        assertEquals(Status.ACP.name(), response.getBody());
    }
    @Test
    public void testInvalidCard_cardDeletion() {
        ValidateCardRequest request = new ValidateCardRequest();
        when(cardService.deleteCard(request)).thenReturn(new Result(Status.RJC, ErrorCode.INVALID_CARD_INFO));
        ResponseEntity<String> response = cardController.deleteCard(request);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(ErrorCode.INVALID_CARD_INFO.name(), response.getBody());
    }
    @Test
    public void testSuccessful_cardValidation() {
        ValidateCardRequest request = new ValidateCardRequest();
        when(cardService.validateCard(request)).thenReturn(new Result(Status.ACP,null));
        ResponseEntity<String> response = cardController.validateCard(request);
        assertEquals(204, response.getStatusCodeValue());
        assertEquals(Status.ACP.name(), response.getBody());
    }
    @Test
    public void testInvalidCardValidation() {
        ValidateCardRequest request = new ValidateCardRequest();
        when(cardService.validateCard(request)).thenReturn(new Result(Status.RJC, ErrorCode.INVALID_CARD_INFO));
        ResponseEntity<String> response = cardController.validateCard(request);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(ErrorCode.INVALID_CARD_INFO.name(), response.getBody());
    }
}
