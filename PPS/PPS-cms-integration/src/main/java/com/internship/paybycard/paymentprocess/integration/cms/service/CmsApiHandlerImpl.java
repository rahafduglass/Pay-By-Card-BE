package com.internship.paybycard.paymentprocess.integration.cms.service;

import com.internship.paybycard.paymentprocess.core.domain.exception.InvalidCardException;
import com.internship.paybycard.paymentprocess.core.domain.exception.ExternalApiNullResponseException;
import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.model.CardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.service.CmsApiHandler;
import com.internship.paybycard.paymentprocess.integration.cms.dto.CardApiResponse;
import com.internship.paybycard.paymentprocess.integration.cms.dto.CmsApiWithdrawRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "cms.api")
public class CmsApiHandlerImpl implements CmsApiHandler {

    @Getter
    @Setter
    private String baseUrl;

    private final Logger log = LoggerFactory.getLogger(CmsApiHandlerImpl.class);

    private WebClient getWebClient() {
        log.info("creating webclient with baseurl {}", baseUrl);
        return WebClient.builder().baseUrl(this.getBaseUrl()).build();
    }

    @Override
    public CardDto verifyCard(VerifyCardDto verifyCardDto) {
        log.info("verifying card with CMS api: {}", verifyCardDto);
        Mono<CardApiResponse> apiResponse = getWebClient().post()
                .uri("/api/v1/cards/validate")
                .bodyValue(verifyCardDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new InvalidCardException("Client error: " + errorBody, ErrorCode.INVALID_CARD)))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Server error: " + errorBody)))
                )
                .bodyToMono(CardApiResponse.class)
                .doOnSuccess(response -> {
                    log.debug("card validated: {}", response.getData());
                })
                .doOnError(error -> {
                    log.error("card validation error: {}", error.getMessage());
                });
        CardApiResponse responseBlock=apiResponse.block();
        if(responseBlock==null){
            log.error("CMS null response");
            throw new ExternalApiNullResponseException("CMS api null response",ErrorCode.EXTERNAL_API_NULL_RESPONSE);
        }
        return responseBlock.getData();
    //    return Objects.requireNonNull(apiResponse.block()).getData(); // todo if apiResponse.block was null what will happen ?
    }

    @Override
    public void withdraw(VerifyCardDto verifyCardDto, BigDecimal amount) {
        log.info("paying by card with CMS api: {}", verifyCardDto);
        CmsApiWithdrawRequest request = new CmsApiWithdrawRequest(verifyCardDto, amount);
      getWebClient().put()
                .uri("/api/v1/cards/withdraw")
              .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Client error: " + errorBody)))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Server error: " + errorBody)))
                )
                .bodyToMono(Void.class)
                .doOnSuccess(response -> {
                    log.debug("card validated ");
                })
                .doOnError(error -> {
                    log.error("card validation error: {}", error.getMessage());
                }).block(); // todo if apiResponse.block was null what will happen ?
    }

}
