package com.internship.paybycard.cardmanagement.application.controller;


import com.internship.paybycard.cardmanagement.application.dtos.response.CreateCardResponse;
import com.internship.paybycard.cardmanagement.application.dtos.response.GetCardResponse;
import com.internship.paybycard.cardmanagement.application.formatter.Formatter;
import com.internship.paybycard.cardmanagement.application.interactors.CreateCardRequest;
import com.internship.paybycard.cardmanagement.application.interactors.UpdateCardRequest;
import com.internship.paybycard.cardmanagement.application.interactors.ValidateCardRequest;
import com.internship.paybycard.cardmanagement.application.interactors.WithdrawRequest;
import com.internship.paybycard.cardmanagement.core.model.CardDto;
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
    private final Formatter formatter;

    @PostMapping
    public ResponseEntity<CreateCardResponse> createCard(@RequestBody @Valid CreateCardRequest createCardRequest) {
        Result<Long> result = cardService.createCard(createCardRequest);
        CreateCardResponse response = new CreateCardResponse(result.data());
        if (result.status().equals(Status.ACP))
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<GetCardResponse> getCard(@PathVariable Long cardId) {
        Result<CardDto> result = cardService.getCard(cardId);
        if (result.status().equals(Status.ACP))
            return ResponseEntity.ok(formatter.toGetCardResponse(result.data()));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }

    @PutMapping
    public ResponseEntity<Result<Void>> updateCard(@RequestBody @Valid UpdateCardRequest updateCardRequest) {
        Result<Void> result = cardService.updateCard(updateCardRequest);
        if (result.status().equals(Status.ACP))
            return ResponseEntity.noContent().build();
        return rejectResponse(result);
    }

    @PostMapping("/validate")
    public ResponseEntity<Result<CardDto>> validateCard(@RequestBody @Valid ValidateCardRequest validateCardRequest) {
        Result<CardDto> result = cardService.validateCard(validateCardRequest);
        if (result.status().equals(Status.ACP))
            return ResponseEntity.status(HttpStatus.OK).body(result);
        return rejectResponse(result);
    }

    @PutMapping("/withdraw")
    public ResponseEntity<Result<Void>> withdraw(@RequestBody @Valid WithdrawRequest withdrawRequest) {
        System.out.println("WITHDRAW API");
        Result<Void> result = cardService.withdraw(withdrawRequest);
        if (result.status().equals(Status.ACP)) {
            return ResponseEntity.noContent().build();
        }
        return rejectResponse(result);
    }

    @DeleteMapping
    public ResponseEntity<Result<Void>> deleteCard(@RequestBody @Valid ValidateCardRequest validateCardRequest) {
        Result<Void> result = cardService.deleteCard(validateCardRequest);
        if (result.status().equals(Status.ACP))
            return ResponseEntity.noContent().build();

        return rejectResponse(result);
    }

    private <T> ResponseEntity<Result<T>> rejectResponse(Result<T> result) {
        return switch (result.errorCode()) {
            case INVALID_CARD_INFO, INSUFFICIENT_CARD_BALANCE ->
                    ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(result);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        };
    }

}
