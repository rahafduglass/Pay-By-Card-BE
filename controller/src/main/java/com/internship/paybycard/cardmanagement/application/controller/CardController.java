package com.internship.paybycard.cardmanagement.application.controller;

import com.internship.paybycard.cardmanagement.application.interactors.CardDto;
import com.internship.paybycard.cardmanagement.application.interactors.CreateCardRequest;
import com.internship.paybycard.cardmanagement.application.interactors.UpdateCardRequest;
import com.internship.paybycard.core.mapper.CardMapper;
import com.internship.paybycard.core.model.CardModel;
import com.internship.paybycard.core.service.CardService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    @Qualifier("cardDtoMapperImpl")
    private final CardMapper<CardModel, CardDto> cardMapper;

    @PostMapping
    public ResponseEntity<String> createCard(@RequestBody CreateCardRequest card) {
        cardService.createCard(cardMapper.mapTo(card));
        return ResponseEntity.ok("Card created successfully");
    }

    @PutMapping
    public ResponseEntity<String> updateCard(@RequestBody UpdateCardRequest card) {
        cardService.updateCard(cardMapper.mapTo(card));
        return ResponseEntity.ok("Card updated successfully");
    }


}
