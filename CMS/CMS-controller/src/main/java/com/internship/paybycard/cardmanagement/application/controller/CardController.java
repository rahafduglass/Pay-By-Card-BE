package com.internship.paybycard.cardmanagement.application.controller;


import com.internship.paybycard.cardmanagement.application.interactors.CreateCardRequest;
import com.internship.paybycard.cardmanagement.application.interactors.UpdateCardRequest;
import com.internship.paybycard.cardmanagement.application.interactors.ValidateCardRequest;
import com.internship.paybycard.cardmanagement.core.result.ErrorCode;
import com.internship.paybycard.cardmanagement.core.result.Result;
import com.internship.paybycard.cardmanagement.core.result.Status;
import com.internship.paybycard.cardmanagement.core.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<String> createCard(@RequestBody @Valid CreateCardRequest createCardRequest) {
        Result result = cardService.createCard(createCardRequest);
        if (result.getStatus().equals(Status.ACP))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result.getStatus().toString());

        return rejectResponse(result.getErrorCode());
    }

    @PutMapping
    public ResponseEntity<String> updateCard(@RequestBody @Valid UpdateCardRequest updateCardRequest) {
        Result result = cardService.updateCard(updateCardRequest);
        if (result.getStatus().equals(Status.ACP))
            return ResponseEntity.ok().body(result.getStatus().toString());

        return rejectResponse(result.getErrorCode());
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateCard(@RequestBody @Valid ValidateCardRequest validateCardRequest) {
        Result result = cardService.validateCard(validateCardRequest);
        if (result.getStatus().equals(Status.ACP))
            return ResponseEntity.ok().body(result.getStatus().toString());


        return rejectResponse(result.getErrorCode());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCard(@RequestBody @Valid ValidateCardRequest validateCardRequest) {
        Result result= cardService.deleteCard(validateCardRequest);
        if (result.getStatus().equals(Status.ACP))
            return ResponseEntity.ok().body(result.getStatus().toString());


        return rejectResponse(result.getErrorCode());
    }

    private ResponseEntity<String> rejectResponse(ErrorCode errorCode) {
        return switch (errorCode) {
            case INVALID_CARD_INFO -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorCode.toString());
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorCode.toString());
        };
    }
}
