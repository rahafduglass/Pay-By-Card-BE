package com.internship.paybycard.paymentprocess.core.infrastructure;

public interface OtpService {
    String generateOtp(String paymentReferenceNumber);
    boolean verifyOtp(String paymentReferenceNumber, String otpCode);
}
