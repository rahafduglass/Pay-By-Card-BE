package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.NullPaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.RealPaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.exception.PaymentNotFoundException;
import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.integration.EmailService;
import com.internship.paybycard.paymentprocess.core.integration.OtpService;
import com.internship.paybycard.paymentprocess.core.persistence.PaymentDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
public class VerifyPaymentModelImplTest {
    @Mock
    private EmailService emailService;

    @Mock
    private OtpService otpService;

    @Mock
    private PaymentDao paymentDao;

    @InjectMocks
    private VerifyPaymentModelImpl verifyPaymentModel;

    @Test
    public void givenValidReferenceNumber_whenCallVerifyPayment_thenSuccess() {
        when(paymentDao.findPaymentByReferenceNumber(any())).thenReturn(new RealPaymentDto());
        verifyPaymentModel = new VerifyPaymentModelImpl("number", paymentDao, otpService, emailService);
        verifyPaymentModel.verifyPayment();
    }

    @Test
    public void givenInvalidReferenceNumber_whenCallVerifyPayment_thenErrorCodePaymentNotFound() {
        when(paymentDao.findPaymentByReferenceNumber(any())).thenReturn(new NullPaymentDto());
        verifyPaymentModel = new VerifyPaymentModelImpl("12312", paymentDao, otpService, emailService);
        verifyPaymentModel.verifyPayment();
        assertEquals(verifyPaymentModel.getErrorCode(), ErrorCode.PAYMENT_NOT_FOUND);
    }


    @Test
    public void callSendOtpWithCallingVerifyPaymentFirst_thenSuccess() {
        RealPaymentDto realPaymentDto = mock(RealPaymentDto.class);
        when(realPaymentDto.getClientEmail()).thenReturn("client@paybycard.com");

        when(paymentDao.findPaymentByReferenceNumber("number")).thenReturn(realPaymentDto);
        when(otpService.generateOtp()).thenReturn("1234");
        doNothing().when(otpService).storeOtp("number", "1234");
        when(emailService.sendOtpEmail("client@paybycard.com", "number", "1234")).thenReturn(true);
        verifyPaymentModel = new VerifyPaymentModelImpl("number", paymentDao, otpService, emailService);
        verifyPaymentModel.verifyPayment();
        assertDoesNotThrow(()->verifyPaymentModel.sendOtp());
    }

}
