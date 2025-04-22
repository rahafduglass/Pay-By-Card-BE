package com.internship.paybycard.cardmanagement.application.controller;


import com.internship.paybycard.cardmanagement.application.interactors.CreateCardRequest;
import com.internship.paybycard.cardmanagement.application.interactors.UpdateCardRequest;
import com.internship.paybycard.core.interactor.CreateCardInteractor;
import com.internship.paybycard.core.interactor.UpdateCardInteractor;
import com.internship.paybycard.core.service.CardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<String> createCard(@RequestBody @Valid CreateCardRequest card) {
        cardService.createCard(card);
        return ResponseEntity.ok("Card created successfully");
    }

    @PutMapping
    public ResponseEntity<String> updateCard(@RequestBody @Valid UpdateCardRequest card) {
        cardService.updateCard((card));
        return ResponseEntity.ok("Card updated successfully");
    }


}
