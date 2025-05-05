package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.dto.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.exception.InvalidPaymentException;
import com.internship.paybycard.paymentprocess.core.persistence.PaymentDao;
import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.model.CardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.service.CmsApiHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class InitiatePaymentModelImplTest {

    @Mock
    private PaymentDao paymentDao;

    @Mock
    private CmsApiHandler cmsApiHandler;

    @InjectMocks
    private InitiatePaymentModelImpl model;

    @Test
    public void testInitiatePaymentModel() {
        CardDto verifiedCard = mock(CardDto.class);
        when(verifiedCard.getCardNumber()).thenReturn("123456789");
        when(verifiedCard.getClientEmail()).thenReturn("client@email.com");

        VerifyCardDto cardTOVerify = mock(VerifyCardDto.class);
        when(cardTOVerify.getCardNumber()).thenReturn("123456789");
        when(cardTOVerify.getExpiryDate()).thenReturn(LocalDate.now());
        when(cardTOVerify.getCVV()).thenReturn("123");

        when(cmsApiHandler.verifyCard(any())).thenReturn(verifiedCard);
        when(paymentDao.createPayment(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        model = new InitiatePaymentModelImpl("items", BigDecimal.valueOf(23444), "client", cardTOVerify, paymentDao, cmsApiHandler);
        model.validatePayment();
        PaymentDto result = model.initiate();
        assertEquals(result.getAmount(), BigDecimal.valueOf(23444));
        assertEquals("client", result.getClientName());
        assertEquals("123456789", result.getCardNumber());
        assertEquals("items", result.getItems());
        assertEquals("123456789", result.getCardNumber());
        assertEquals(false, result.getConfirmed());
        assertEquals("client@email.com", result.getClientEmail());

    }

    @Test
    public void testValidatePaymentModel_validInput() {
        VerifyCardDto cardTOVerify = mock(VerifyCardDto.class);
        when(cardTOVerify.getCardNumber()).thenReturn("123456789");
        when(cardTOVerify.getExpiryDate()).thenReturn(LocalDate.now());
        when(cardTOVerify.getCVV()).thenReturn("123");

        model = new InitiatePaymentModelImpl("items", BigDecimal.valueOf(23444), "client", cardTOVerify, paymentDao, cmsApiHandler);
        assertTrue(model.validatePayment());
    }

    @Test
    public void testValidatePaymentModel_invalidPaymentInput() {
        VerifyCardDto cardTOVerify = mock(VerifyCardDto.class);
        when(cardTOVerify.getCardNumber()).thenReturn("123456789");
        when(cardTOVerify.getExpiryDate()).thenReturn(LocalDate.now());
        when(cardTOVerify.getCVV()).thenReturn("123");

        model = new InitiatePaymentModelImpl("", BigDecimal.ZERO, "client", cardTOVerify, paymentDao, cmsApiHandler);
        Exception exception = assertThrows(InvalidPaymentException.class, () -> model.validatePayment());
        assertEquals("invalid payment input", exception.getMessage());
    }

    @Test
    public void testValidatePaymentModel_invalidCardInput() {
        VerifyCardDto cardTOVerify = mock(VerifyCardDto.class);
        when(cardTOVerify.getCardNumber()).thenReturn("");
        when(cardTOVerify.getExpiryDate()).thenReturn(LocalDate.now());
        when(cardTOVerify.getCVV()).thenReturn("123");

        model = new InitiatePaymentModelImpl("items", BigDecimal.valueOf(23444), "client", cardTOVerify, paymentDao, cmsApiHandler);
        Exception exceptionEmptyCardNumber = assertThrows(InvalidPaymentException.class, () -> model.validatePayment());
        assertEquals("invalid card input", exceptionEmptyCardNumber.getMessage());


        when(cardTOVerify.getCardNumber()).thenReturn("1234455");
        when(cardTOVerify.getExpiryDate()).thenReturn(null);
        when(cardTOVerify.getCVV()).thenReturn("123");

        Exception exceptionNullExpiryDate = assertThrows(InvalidPaymentException.class, () -> model.validatePayment());
        assertEquals("invalid card input", exceptionNullExpiryDate.getMessage());
    }
}
