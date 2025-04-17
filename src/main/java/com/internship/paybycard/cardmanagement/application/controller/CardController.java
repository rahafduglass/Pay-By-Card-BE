package com.internship.paybycard.cardmanagement.application.controller;

import com.internship.paybycard.cardmanagement.application.dto.ApiResponse;
import com.internship.paybycard.cardmanagement.application.dto.CardRequest;
import com.internship.paybycard.cardmanagement.domain.mapper.CardMapper;
import com.internship.paybycard.cardmanagement.domain.service.CardService;
import com.internship.paybycard.cardmanagement.persistence.adapter.CardAdapter;
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
    public ResponseEntity<ApiResponse<Boolean>> createCard(@RequestBody CardRequest card) {
        try {
            boolean isSuccess = cardService.createCard(cardMapper.requestToModel(card));
            ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder()
                    .success(isSuccess)
                    .message(isSuccess ? "Card created successfully" : "Card creation failed")
                    .response(isSuccess)
                    .build();
            return ResponseEntity
                    .status(isSuccess ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                    .body(apiResponse);
        } catch (Exception e) {
            ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder()
                    .success(false)
                    .message("an error occurred" + e.getMessage())
                    .response(false)
                    .build();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }
}
