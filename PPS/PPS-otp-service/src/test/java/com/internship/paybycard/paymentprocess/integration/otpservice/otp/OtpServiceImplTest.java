package com.internship.paybycard.paymentprocess.integration.otpservice.otp;


import com.internship.paybycard.paymentprocess.OtpTestApp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = OtpTestApp.class)
public class OtpServiceImplTest {

    @Autowired
    private OtpServiceImpl otpServiceimpl;


    @Test
    public void whenCallGenerateOtp_thenReturnOtp() {
        String otp= otpServiceimpl.generateOtp();
        assertNotNull(otp);
        assertEquals(4, otp.length());
    }

    @Test
    public void givenReferenceNumberAndOtp_whenCallStoreOtp_thenShouldReturnOtpWhenCallGetOtp(){
        String referenceNumber = UUID.randomUUID().toString();
        String otp= otpServiceimpl.generateOtp();
        otpServiceimpl.storeOtp(referenceNumber,otp);
        String retrievedOtp= otpServiceimpl.getOtp(referenceNumber);
        assertNotNull(retrievedOtp);
        assertEquals(otp,retrievedOtp);
    }
    @Test
    public void givenValidPaymenetReferenceAndOtp_whenCallVerifyOtp_theShouldReturnTrue(){
        String referenceNumber = UUID.randomUUID().toString();
        String otp= otpServiceimpl.generateOtp();
        otpServiceimpl.storeOtp(referenceNumber,otp);
        boolean result = otpServiceimpl.verifyOtp(referenceNumber,otp);
        assertTrue(result);
    }

    @Test
    public void givenInvalidPayementReferenceAndOtp_whenCallVerifyOtp_theShouldReturnFalse(){
        String referenceNumber = UUID.randomUUID().toString();
        String otp= otpServiceimpl.generateOtp();
        boolean result =otpServiceimpl.verifyOtp(referenceNumber,"1234");
        assertFalse(result);
    }

}
