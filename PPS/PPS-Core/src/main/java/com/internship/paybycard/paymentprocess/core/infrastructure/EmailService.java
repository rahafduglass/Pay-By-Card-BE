package com.internship.paybycard.paymentprocess.core.infrastructure;

public interface EmailService {
    void sendOtpEmail(String emailAddress, String paymentReferenceNumber ,String otpCode);
}
