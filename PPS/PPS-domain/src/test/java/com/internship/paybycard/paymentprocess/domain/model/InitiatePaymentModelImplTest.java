package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.RealPaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.model.CardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.service.CmsApiHandler;
import com.internship.paybycard.paymentprocess.core.persistence.PaymentDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InitiatePaymentModelImplTest {
    @Mock
    private PaymentDao paymentDao;

    @Mock
    private CmsApiHandler cmsApiHandler;

    @Mock
    private VerifyCardDto card;

    @Mock
    private CardDto verifiedCard;

    @InjectMocks
    private InitiatePaymentModelImpl model;

    @BeforeEach
    public void setup() {
        when(card.getCardNumber()).thenReturn(UUID.randomUUID().toString());
        model = InitiatePaymentModelImpl.builder()
                .items("items")
                .amount(new BigDecimal(10))
                .clientName("clientName")
                .card(card)
                .paymentDao(paymentDao)
                .cmsApiHandler(cmsApiHandler)
                .build();
    }

    @Test
    public void givenAmountBiggerThanBalanceWithCmsApiHandlerReturnVerifiedCard_whenCallValidPayment_thenErrorCodeShouldReturnInsufficientBalance() {
        when(cmsApiHandler.verifyCard(any(VerifyCardDto.class))).thenReturn(verifiedCard);
        when(verifiedCard.getBalance()).thenReturn(new BigDecimal(5));

        model.validatePayment();
        assertEquals(ErrorCode.INSUFFICIENT_CARD_BALANCE, model.getErrorCode());
        assertFalse(model.isPaymentValid());
    }

    @Test
    public void givenInvalidCardWithCmsApiHandlerReturnNull_whenCallValidPayment_thenErrorCodeShouldReturnInvalidCard() {
        when(cmsApiHandler.verifyCard(any(VerifyCardDto.class))).thenReturn(null);
        model.validatePayment();
        assertEquals(ErrorCode.INVALID_CARD, model.getErrorCode());
        assertFalse(model.isPaymentValid());
    }

    @Test
    public void givenValidModelDetails_whenCallValidPayment_thenIsPaymentValidReturnsTrue() {
        when(cmsApiHandler.verifyCard(any(VerifyCardDto.class))).thenReturn(verifiedCard);
        when(verifiedCard.getBalance()).thenReturn(new BigDecimal(1000));

        model.validatePayment();
        assertTrue(model.isPaymentValid());
    }

    @Test
    public void givenUnexpectedError_whenCallValidPayment_thenErrorCodeShouldReturnInternalServerError() {
        doThrow(RuntimeException.class).when(cmsApiHandler).verifyCard(any(VerifyCardDto.class));
        model.validatePayment();
        assertEquals(ErrorCode.INTERNAL_SERVER_ERROR, model.getErrorCode());
        assertFalse(model.isPaymentValid());
    }

    @Test
    public void givenValidPayment_whenCallProcess_thenResponseShouldNotBeNull() {
        when(cmsApiHandler.verifyCard(any(VerifyCardDto.class))).thenReturn(verifiedCard);
        when(verifiedCard.getBalance()).thenReturn(new BigDecimal(1000));
        model.validatePayment();

        when(paymentDao.createPayment(any(RealPaymentDto.class))).thenReturn(new RealPaymentDto());
        model.process();
        assertNotNull(model.getResponse());
    }

    @Test
    public void givenInvalidPayment_whenCallProcess_thenResponseShouldBeNull() {
        when(cmsApiHandler.verifyCard(any(VerifyCardDto.class))).thenReturn(null);
        model.validatePayment();

        model.process();
        assertNull(model.getResponse());
    }

    @Test
    public void givenUnexpectedError_whenCallProcess_thenErrorCodeShouldReturnInternalServerError() {
        when(cmsApiHandler.verifyCard(any(VerifyCardDto.class))).thenReturn(verifiedCard);
        when(verifiedCard.getBalance()).thenReturn(new BigDecimal(1000));
        model.validatePayment();

        doThrow(RuntimeException.class).when(paymentDao).createPayment(any(RealPaymentDto.class));
        model.process();
        assertEquals(ErrorCode.INTERNAL_SERVER_ERROR, model.getErrorCode());
        assertFalse(model.isPaymentValid());
    }
}
