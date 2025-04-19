package com.internship.paybycard.cardmanagement.application.controller;

import com.internship.paybycard.cardmanagement.api.dto.CardRequest;
import com.internship.paybycard.cardmanagement.api.mapper.CardMapper;
import com.internship.paybycard.cardmanagement.domain.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        try {
            boolean isSuccess = cardService.createCard(cardMapper.requestToModel(card));
            String apiResponse = isSuccess ? "Card created successfully" : "Card creation failed";
            return ResponseEntity
                    .status(isSuccess ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                    .body(apiResponse);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("an error occurred" + e.getMessage());
        }
    }

}
