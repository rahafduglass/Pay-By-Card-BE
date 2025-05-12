package com.internship.paybycard.paymentprocess.integration;

import com.internship.paybycard.paymentprocess.core.integration.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class EmailServiceImpl implements EmailService {
    private final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    // todo did u manage to test it
    @Override
    public boolean sendOtpEmail(String emailAddress, String paymentReferenceNumber, String otpCode) {
        log.info("Sending OTP email to " + emailAddress);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailAddress);
        message.setSubject("OTP Code");
        message.setText("OTP Code: " + otpCode +"\nPayment Reference: " + paymentReferenceNumber);
        mailSender.send(message);
        return true;
    }
}
