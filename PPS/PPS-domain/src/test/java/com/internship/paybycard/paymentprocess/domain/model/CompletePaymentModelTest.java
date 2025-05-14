package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.exception.InvalidOtpException;
import com.internship.paybycard.paymentprocess.core.domain.exception.PaymentNotFoundException;
import com.internship.paybycard.paymentprocess.core.domain.exception.PersistenceException;
import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.integration.OtpService;
import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.service.CmsApiHandler;
import com.internship.paybycard.paymentprocess.core.persistence.PaymentDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CompletePaymentModelTest {

    @Mock
    private OtpService otpService;

    @Mock
    private CmsApiHandler cmsApiHandler;

    @Mock
    private PaymentDao paymentDao;

    @InjectMocks
    private CompletePaymentModelImpl model;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        otpService = mock(OtpService.class);
        model = CompletePaymentModelImpl.builder()
                .otpService(otpService)
                .cmsApiHandler(cmsApiHandler)
                .paymentDao(paymentDao)
                .referenceNumber("123455667878")
                .OTP("1234")
                .verifyCardDto(null)
                .build();
    }

    @Test
    public void givenValidOtp_whenCallVerifyOtp_shouldDoNothing() {
        when(otpService.verifyOtp(any(), any())).thenReturn(true);
        assertDoesNotThrow(() -> model.verifyOTP());
    }

    @Test
    public void givenInvalidOtp_whenCallVerifyOtp_thenErrorCodeShouldBeInvalidOtp() {
        when(otpService.verifyOtp(any(), any())).thenReturn(false);
        model.verifyOTP();
        assertEquals(ErrorCode.INVALID_OR_EXPIRED_OTP, model.getErrorCode());
    }

    @Test
    public void givenValidPayment_whenCallVerifyOtpThenCallPay_thenErrorCodeShouldBeSuccess() {
        givenValidOtp_whenCallVerifyOtp_shouldDoNothing();

        PaymentDto retrievedPayment = mock(PaymentDto.class);
        when(retrievedPayment.isNull()).thenReturn(false);
        when(retrievedPayment.getAmount()).thenReturn(BigDecimal.ONE);

        when(paymentDao.findPaymentByReferenceNumber(any())).thenReturn(retrievedPayment);
        when(paymentDao.updatePaymentConfirmed(any(), any())).thenReturn(1);
        doNothing().when(cmsApiHandler).withdraw(any(VerifyCardDto.class), any(BigDecimal.class));

        model.pay();
        assertEquals(ErrorCode.SUCCESS, model.getErrorCode());
    }

    @Test
    public void givenInvalidReferenceNumber_whenCallVerifyOtpThenCallPay_shouldThrowPaymentNotFoundException() {

        PaymentDto retrievedPayment = mock(PaymentDto.class);
        when(retrievedPayment.isNull()).thenReturn(true);
        when(paymentDao.findPaymentByReferenceNumber(any())).thenReturn(retrievedPayment);

        assertThrows(PaymentNotFoundException.class, () -> model.pay());
    }

    @Test
    public void givenValidPaymentWithPaymentDaoNotUpdating_whenCallPay_shouldThrowPersistenceException() {
        givenValidOtp_whenCallVerifyOtp_shouldDoNothing();

        PaymentDto retrievedPayment = mock(PaymentDto.class);
        when(retrievedPayment.isNull()).thenReturn(false);
        when(paymentDao.findPaymentByReferenceNumber(any())).thenReturn(retrievedPayment);
        doNothing().when(cmsApiHandler).withdraw(any(VerifyCardDto.class), any(BigDecimal.class));
        when(paymentDao.updatePaymentConfirmed(any(), any())).thenReturn(0);
        assertThrows(PersistenceException.class, () -> model.pay());
    }
}
