package com.internship.paybycard.paymentprocess.infrastructure.otp;

import com.internship.paybycard.paymentprocess.core.infrastructure.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public String getOtp(String paymentReferenceNumber) {
        return redisTemplate.opsForValue().get("OTP:" + paymentReferenceNumber);
    }

    @Override
    public void storeOtp(String paymentReferenceNumber,String otp) {
        redisTemplate.opsForValue().set("OTP:" + paymentReferenceNumber, otp,2, TimeUnit.MINUTES);
    }

    @Override
    public String generateOtp(){
        return String.valueOf((int)(Math.random() * 9000) + 1000);
    }

    @Override
    public boolean verifyOtp(String paymentReferenceNumber, String otp) {
        return getOtp(paymentReferenceNumber).equals(otp);
    }
}
