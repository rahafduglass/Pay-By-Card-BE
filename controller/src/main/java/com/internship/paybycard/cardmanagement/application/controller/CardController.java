package com.internship.paybycard.cardmanagement.application.controller;


import com.internship.paybycard.cardmanagement.application.interactors.CreateCardRequest;
import com.internship.paybycard.cardmanagement.application.interactors.UpdateCardRequest;
import com.internship.paybycard.cardmanagement.application.interactors.ValidateCardRequest;
import com.internship.paybycard.core.service.CardService;

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
        cardService.createCard(createCardRequest);
        return ResponseEntity.ok("Card created successfully");
    }

    @PutMapping
    public ResponseEntity<String> updateCard(@RequestBody @Valid UpdateCardRequest updateCardRequest) {
        cardService.updateCard(updateCardRequest);
        return ResponseEntity.ok("Card updated successfully");
    }

    @GetMapping
    public ResponseEntity<Boolean> validateCard(@RequestBody @Valid ValidateCardRequest validateCardRequest) {
        return ResponseEntity.ok().body(cardService.validateCard(validateCardRequest));
    }
}
