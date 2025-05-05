package com.internship.paybycard.paymentprocess.core.integration;

public interface EmailService {
    void sendOtpEmail(String emailAddress, String paymentReferenceNumber ,String otpCode);
}
