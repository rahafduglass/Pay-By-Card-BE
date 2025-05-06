package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.dto.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.exception.EmptyReferenceNumberException;
import com.internship.paybycard.paymentprocess.core.domain.exception.PaymentNotFoundException;
import com.internship.paybycard.paymentprocess.core.domain.model.VerifyPaymentModel;
import com.internship.paybycard.paymentprocess.core.integration.EmailService;
import com.internship.paybycard.paymentprocess.core.integration.OtpService;
import com.internship.paybycard.paymentprocess.core.persistence.PaymentDao;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@RequiredArgsConstructor
public class VerifyPaymentModelImpl implements VerifyPaymentModel {

    private final String referenceNumber;

    private boolean isVerified = false;
    private PaymentDto paymentDto;

    private final PaymentDao paymentDao;
    private final OtpService otpService;
    private final EmailService emailService;

    private final Logger log = LoggerFactory.getLogger(VerifyPaymentModelImpl.class);

    @Override
    public void verifyPayment() {
        log.info("Verifying payment with reference number " + referenceNumber);
        if (referenceNumber == null || referenceNumber.isEmpty()) {
            log.error("reference number is null or empty");
            throw new EmptyReferenceNumberException("empty reference number");
        }
        log.debug("Verifying payment with reference number " + referenceNumber + " by paymentDao");
        paymentDto = paymentDao.findPaymentByReferenceNumber(referenceNumber);
        if(paymentDto.isNull()) {
            log.error("Payment with reference number " + referenceNumber + " not found");
            throw new PaymentNotFoundException("payment not found " + referenceNumber);
        }
        isVerified = true;
    }

    @Override
    public void sendOtp() {
        log.info("Sending OTP with reference number= {}", referenceNumber);
        if (isVerified) {
            log.debug("generating OTP for reference number= {}", referenceNumber);
            String otp = otpService.generateOtp();
            log.debug("storing OTP for reference number= {}", referenceNumber);
            otpService.storeOtp(referenceNumber, otp);
            log.debug("sending OTP to email address= {}", paymentDto.getClientEmail());
            emailService.sendOtpEmail(paymentDto.getClientEmail(), referenceNumber, otp);
        } else {
            log.error("Payment with reference number " + referenceNumber + " not verified");
            throw new PaymentNotFoundException("payment does not exist OR you haven't called verifyPayment() method first");
        }
    }

}
