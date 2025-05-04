package com.internship.paybycard.paymentprocess.infrastructure.cms.service;

import com.internship.paybycard.paymentprocess.core.infrastructure.cms.dto.VerifyCardDto;
import com.internship.paybycard.paymentprocess.core.infrastructure.cms.model.CardDto;
import com.internship.paybycard.paymentprocess.core.infrastructure.cms.service.CmsApiHandler;
import com.internship.paybycard.paymentprocess.infrastructure.cms.dto.CardApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CmsApiHandlerImpl implements CmsApiHandler {

    private final WebClient client = WebClient.create("http://localhost:8080");

    @Override
    public CardDto verifyCard(VerifyCardDto verifyCardDto) {
        Mono<CardApiResponse> apiResponse = client.post()
                .uri("/api/v1/cards/validate")
                .bodyValue(verifyCardDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Client error: " + errorBody)))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Server error: " + errorBody)))
                )
                .bodyToMono(CardApiResponse.class)
                .doOnSuccess(response -> {

                    System.out.println("✅ Card is valid");

                })
                .doOnError(error -> {
                    System.err.println("❌ Error validating card: " + error.getMessage());
                });
        return Objects.requireNonNull(apiResponse.block()).getData();
    }

    @Override
    public void pay(VerifyCardDto verifyCardDto, BigDecimal amount) {

    }

}
