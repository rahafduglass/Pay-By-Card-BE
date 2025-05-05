package com.internship.paybycard.paymentprocess.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = EmailServiceTestApp.class)
public class EmailServiceImplTest {
    @Autowired
    private EmailServiceImpl emailService;

    @Test
    public void givenValidEmail_whenCallSendOtpEmail_thenSuccess() {
        boolean isSent = emailService.sendOtpEmail("rahooof@gmail.com", "12345", "OTP");
        assertTrue(isSent);
    }
}
