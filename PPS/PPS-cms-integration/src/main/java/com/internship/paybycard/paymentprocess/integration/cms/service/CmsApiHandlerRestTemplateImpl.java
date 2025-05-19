package com.internship.paybycard.paymentprocess.integration.cms.service;

import com.internship.paybycard.paymentprocess.core.domain.exception.ExternalApiNullResponseException;
import com.internship.paybycard.paymentprocess.core.domain.exception.InvalidCardException;
import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.model.CardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.service.CmsApiHandler;
import com.internship.paybycard.paymentprocess.integration.cms.config.CmsApiProperties;
import com.internship.paybycard.paymentprocess.integration.cms.dto.CardApiResponse;
import com.internship.paybycard.paymentprocess.integration.cms.dto.CmsApiWithdrawRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class CmsApiHandlerRestTemplateImpl implements CmsApiHandler {


    private final CmsApiProperties cmsApiProperties;

    private final RestTemplate restTemplate;


    @Value("${jwt.secret-key}")
    @Setter
    @Getter
    private String secretKey;

    public CmsApiHandlerRestTemplateImpl(CmsApiProperties cmsApiProperties) {
        this.restTemplate = new RestTemplate();
        this.cmsApiProperties = cmsApiProperties;
    }

    @Override
    public CardDto verifyCard(VerifyCardDto verifyCardDto) {
        Key secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode( getSecretKey()));

        try {
            long oneDayFromNow = System.currentTimeMillis() + 86400000L;
            String jwt = Jwts.builder().setSubject("payment-service-request")
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(oneDayFromNow))
                    .signWith(SignatureAlgorithm.HS256,secretKey)
                    .compact();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(jwt);
            HttpEntity<VerifyCardDto> request = new HttpEntity<>(verifyCardDto, headers);

            ResponseEntity<CardApiResponse> response = restTemplate.postForEntity(
                    cmsApiProperties.getBaseUrl() + "/api/v1/cards/validate",
                    request,
                    CardApiResponse.class
            );
            if (response.getBody() == null) {
                throw new ExternalApiNullResponseException("CMS api null response", ErrorCode.EXTERNAL_API_NULL_RESPONSE);
            }
            return response.getBody().getData();
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
                throw new InvalidCardException("Client error: " + e.getResponseBodyAsString(), ErrorCode.INVALID_CARD);
            }
            throw new RuntimeException("Client error: " +e.getStatusCode()+ e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new RuntimeException("Error during CMS API call", e);
        }
    }

    @Override
    public void withdraw(VerifyCardDto verifyCardDto, BigDecimal amount) {
        try {
            CmsApiWithdrawRequest request = new CmsApiWithdrawRequest(verifyCardDto, amount);
            restTemplate.put(
                    cmsApiProperties.getBaseUrl() + "/api/v1/cards/withdraw",
                    request
            );
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Client error: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error during CMS API withdraw call", e);
        }
    }

}
