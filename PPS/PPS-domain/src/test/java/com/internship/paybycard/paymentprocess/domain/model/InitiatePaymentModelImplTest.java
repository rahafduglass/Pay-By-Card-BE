package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.exception.InsufficientCardBalance;
import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.model.CardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.service.CmsApiHandler;
import com.internship.paybycard.paymentprocess.core.persistence.PaymentDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InitiatePaymentModelImplTest {
    @Mock
    private PaymentDao paymentDao;

    @Mock
    private CmsApiHandler cmsApiHandler;

    @Mock
    private VerifyCardDto cardTOVerify;

    @InjectMocks
    private InitiatePaymentModelImpl model;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        cardTOVerify = mock(VerifyCardDto.class);
        when(cardTOVerify.getCardNumber()).thenReturn("123456789");
        when(cardTOVerify.getExpiryDate()).thenReturn(LocalDate.now());
        when(cardTOVerify.getCVV()).thenReturn("123");

        model = InitiatePaymentModelImpl.builder()
                .card(cardTOVerify)
                .items("items")
                .clientName("client")
                .amount(BigDecimal.valueOf(23444))
                .cmsApiHandler(cmsApiHandler)
                .paymentDao(paymentDao)
                .build();
    }

    @Test
    public void givenValidModel_whenCallValidateCardThenProcess_shouldReturnSavedPaymentDto() {
        CardDto cmsCardResponse = mock(CardDto.class);
        when(cmsCardResponse.getCardNumber()).thenReturn("123456789");
        when(cmsCardResponse.getClientEmail()).thenReturn("client@email.com");
        when(cmsCardResponse.getBalance()).thenReturn(BigDecimal.valueOf(3000000.5454));

        when(cmsApiHandler.verifyCard(any())).thenReturn(cmsCardResponse);
        when(paymentDao.createPayment(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        model.validatePayment();
        PaymentDto result = model.process();
        assertEquals(result.getAmount(), BigDecimal.valueOf(23444));
        assertEquals("client", result.getClientName());
        assertEquals("123456789", result.getCardNumber());
        assertEquals("items", result.getItems());
        assertEquals("123456789", result.getCardNumber());
        assertEquals(false, result.getConfirmed());
        assertEquals("client@email.com", result.getClientEmail());
    }

    @Test
    public void givenIsPaymentValidAsFalse_whenCallProcess_shouldThrowRunTimeException() {
        assertThrows(RuntimeException.class, () -> model.process());
    }

    @Test
    public void givenInvalidAmount_whenCallValidatePayment_shouldThrowInsufficientCardBalanceException() {
        CardDto cmsCardResponse = mock(CardDto.class);

        when(cmsCardResponse.getBalance()).thenReturn(BigDecimal.valueOf(344));
        when(cmsApiHandler.verifyCard(any())).thenReturn(cmsCardResponse);

        assertThrows(InsufficientCardBalance.class, () -> model.validatePayment());
    }

    @Test
    public void givenValidPaymentModel_whenCallValidatePayment_shouldReturnTrue() {
        CardDto cmsCardResponse = mock(CardDto.class);

        when(cmsCardResponse.getBalance()).thenReturn(BigDecimal.valueOf(3000000.5454));
        when(cmsApiHandler.verifyCard(any())).thenReturn(cmsCardResponse);

        assertTrue(model.validatePayment());
    }


}
