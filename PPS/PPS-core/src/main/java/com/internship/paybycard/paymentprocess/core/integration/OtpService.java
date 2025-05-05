package com.internship.paybycard.paymentprocess.core.integration;

public interface OtpService {
    String getOtp(String paymentReferenceNumber);
    boolean verifyOtp(String paymentReferenceNumber, String otpCode);
    void storeOtp(String paymentReferenceNumber,String otp);
    String generateOtp();
}
