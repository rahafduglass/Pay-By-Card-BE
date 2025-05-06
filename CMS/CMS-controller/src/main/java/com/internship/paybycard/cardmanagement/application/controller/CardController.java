package com.internship.paybycard.cardmanagement.application.controller;


import com.internship.paybycard.cardmanagement.application.interactors.CreateCardRequest;
import com.internship.paybycard.cardmanagement.application.interactors.UpdateCardRequest;
import com.internship.paybycard.cardmanagement.application.interactors.ValidateCardRequest;
import com.internship.paybycard.cardmanagement.core.model.CardDto;
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
    // todo unit test
    private final CardService cardService;

    @PostMapping
    public ResponseEntity<Result<Void>> createCard(@RequestBody @Valid CreateCardRequest createCardRequest) {
        Result<Void> result = cardService.createCard(createCardRequest);
        if (result.status().equals(Status.ACP))
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        return rejectResponse(result);
    }

    @PutMapping
    public ResponseEntity<Result<Void>> updateCard(@RequestBody @Valid UpdateCardRequest updateCardRequest) {
        Result<Void> result = cardService.updateCard(updateCardRequest);
        if (result.status().equals(Status.ACP))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
        return rejectResponse(result);
    }

    @PostMapping("/validate")
    public ResponseEntity<Result<CardDto>> validateCard(@RequestBody @Valid ValidateCardRequest validateCardRequest) {
        Result<CardDto> result = cardService.validateCard(validateCardRequest);
        if (result.status().equals(Status.ACP))
            return ResponseEntity.status(HttpStatus.OK).body(result);
        return rejectResponse(result);
    }

    @DeleteMapping
    public ResponseEntity<Result<Void>> deleteCard(@RequestBody @Valid ValidateCardRequest validateCardRequest) {
        Result<Void> result = cardService.deleteCard(validateCardRequest);
        if (result.status().equals(Status.ACP))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);

        return rejectResponse(result);
    }

    private <T> ResponseEntity<Result<T>> rejectResponse(Result<T> result) {
        return switch (result.errorCode()) {
            case INVALID_CARD_INFO -> ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(result);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        };
    }
}
