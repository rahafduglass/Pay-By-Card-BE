package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.exception.PaymentNotFoundException;
import com.internship.paybycard.paymentprocess.core.domain.model.VerifyPaymentModel;
import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.integration.EmailService;
import com.internship.paybycard.paymentprocess.core.integration.OtpService;
import com.internship.paybycard.paymentprocess.core.persistence.PaymentDao;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public class VerifyPaymentModelImpl implements VerifyPaymentModel {
    private final String referenceNumber;
    private final PaymentDao paymentDao;
    private final OtpService otpService;
    private final EmailService emailService;

    private boolean isVerified = false;
    private PaymentDto paymentDto;

    @Override
    public void verifyPayment() {
        log.info("Verifying payment with reference number " + referenceNumber);

        paymentDto = paymentDao.findPaymentByReferenceNumber(referenceNumber);
        if(paymentDto.isNull()) {
            log.error("Payment with reference number " + referenceNumber + " not found");
            throw new PaymentNotFoundException("payment not found " + referenceNumber, ErrorCode.PAYMENT_NOT_FOUND);
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
            throw new PaymentNotFoundException("payment does not exist OR you haven't called verifyPayment() method first",ErrorCode.PAYMENT_NOT_FOUND);
        }
    }
}
