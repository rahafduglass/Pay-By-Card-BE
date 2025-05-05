package com.internship.paybycard.paymentprocess.integration.otpservice.otp;

import com.internship.paybycard.paymentprocess.core.integration.OtpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Component
public class OtpServiceImpl implements OtpService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final Logger log = LoggerFactory.getLogger(OtpServiceImpl.class);

    @Override
    public String getOtp(String paymentReferenceNumber) {
        log.debug("getting otp for payment reference number {}", paymentReferenceNumber);
        return redisTemplate.opsForValue().get("OTP:" + paymentReferenceNumber);
    }

    @Override
    public void storeOtp(String paymentReferenceNumber,String otp) {
        log.debug("storing otp for payment reference number {}", paymentReferenceNumber);
        redisTemplate.opsForValue().set("OTP:" + paymentReferenceNumber, otp,2, TimeUnit.MINUTES);
    }

    @Override
    public String generateOtp(){
        log.debug("generating random otp");
        return String.valueOf((int)(Math.random() * 9000) + 1000);
    }

    @Override
    public boolean verifyOtp(String paymentReferenceNumber, String otp) {
        log.debug("verifying otp for payment reference number {}", paymentReferenceNumber);
        String storedOtp=getOtp(paymentReferenceNumber);
        return storedOtp != null && storedOtp.equals(otp);
    }
}
