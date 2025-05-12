package com.internship.paybycard.paymentprocess.integration.cms.service;

import com.internship.paybycard.paymentprocess.core.domain.dto.card.VerifyCardDtoImpl;
import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.model.CardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.service.CmsApiHandler;
import com.internship.paybycard.paymentprocess.integration.cms.CmsTestApp;
import com.internship.paybycard.paymentprocess.integration.cms.dto.CmsApiWithdrawRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = CmsTestApp.class)
public class CmsApiHandlerImplTest {

    @Qualifier("cmsApiHandlerRestTemplateImpl")
    @Autowired
    private CmsApiHandler cmsApiHandlerImpl;

    @Test
    public void givenValidRequest_whenCallVerifyCard_thenReturnCardDto(){
        VerifyCardDtoImpl verifyCardDto = new VerifyCardDtoImpl();
        verifyCardDto.setCardNumber("b2583dee-4c27-45fc-9df0-c779f6531a39"); // from db record xD
        verifyCardDto.setCVV("123");
        verifyCardDto.setExpiryDate(LocalDate.parse("2027-05-01"));
        CardDto cardDto = cmsApiHandlerImpl.verifyCard(verifyCardDto);
        assertNotNull(cardDto);
    }

    @Test
    public void givenValidRequest_whenCallWithdrawCard_thenReturnNothing(){
        VerifyCardDtoImpl verifyCardDto = new VerifyCardDtoImpl();
        verifyCardDto.setCardNumber("b2583dee-4c27-45fc-9df0-c779f6531a39"); // from db record xD
        verifyCardDto.setCVV("123");
        verifyCardDto.setExpiryDate(LocalDate.parse("2027-05-01"));
        BigDecimal amount = new BigDecimal("33");
        cmsApiHandlerImpl.withdraw(verifyCardDto, amount);

    }

}
