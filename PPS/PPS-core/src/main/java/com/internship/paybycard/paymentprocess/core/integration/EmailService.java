package com.internship.paybycard.paymentprocess.core.integration;

public interface EmailService {
    boolean sendOtpEmail(String emailAddress, String paymentReferenceNumber ,String otpCode);
}
