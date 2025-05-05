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

@Getter
@RequiredArgsConstructor
public class VerifyPaymentModelImpl implements VerifyPaymentModel {

    private final String referenceNumber;

    private boolean isVerified = false;
    private PaymentDto paymentDto;

    private final PaymentDao paymentDao;
    private final OtpService otpService;
    private final EmailService emailService;


    @Override
    public void verifyPayment() {
        if (referenceNumber == null || referenceNumber.isEmpty()) {
            throw new EmptyReferenceNumberException("empty reference number");
        }
        paymentDto = paymentDao.findPaymentByReferenceNumber(referenceNumber);
        if (paymentDto == null) {
            throw new PaymentNotFoundException("payment not found " + referenceNumber);
        }
        isVerified = true;
    }

    @Override
    public void sendOtp() {
        if (isVerified) {
            String otp = otpService.generateOtp();
            otpService.storeOtp(referenceNumber, otp);
            emailService.sendOtpEmail(paymentDto.getClientEmail(), referenceNumber, otp);
        } else throw new PaymentNotFoundException("payment does not exist OR you haven't called verifyPayment() method first");
    }

}
