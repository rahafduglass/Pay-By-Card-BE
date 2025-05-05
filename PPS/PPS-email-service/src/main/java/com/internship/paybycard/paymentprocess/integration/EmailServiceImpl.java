package com.internship.paybycard.paymentprocess.integration;

import com.internship.paybycard.paymentprocess.core.integration.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public boolean sendOtpEmail(String emailAddress, String paymentReferenceNumber, String otpCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailAddress);
        message.setSubject("OTP Code");
        message.setText("OTP Code: " + otpCode +"\nPayment Reference: " + paymentReferenceNumber);
        mailSender.send(message);
        return true;
    }
}
