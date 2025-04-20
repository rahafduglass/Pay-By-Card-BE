package com.internship.paybycard.cardmanagement.application.controller;

import com.internship.paybycard.cardmanagement.application.pojo.CardRequest;
import com.internship.paybycard.core.mapper.CardMapper;
import com.internship.paybycard.core.model.CardModel;
import com.internship.paybycard.core.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    @Qualifier("cardMapperImpl")
    private final CardMapper<CardModel, CardRequest> cardMapper;

    @PostMapping
    public ResponseEntity<String> createCard(@RequestBody CardRequest card) {
        cardService.createCard(cardMapper.mapToModel(card));
        return ResponseEntity.ok("Card created successfully");
    }

}
