package com.internship.paybycard.cardmanagement.application.controller;

import com.internship.paybycard.cardmanagement.api.dto.CardRequest;
import com.internship.paybycard.cardmanagement.api.mapper.CardMapper;
import com.internship.paybycard.cardmanagement.domain.service.CardService;
import lombok.RequiredArgsConstructor;
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
    private final CardMapper cardMapper;

    @PostMapping
    public ResponseEntity<String> createCard(@RequestBody CardRequest card) {
        cardService.createCard(cardMapper.requestToModel(card));
        return ResponseEntity.ok("Card created successfully");
    }

}
