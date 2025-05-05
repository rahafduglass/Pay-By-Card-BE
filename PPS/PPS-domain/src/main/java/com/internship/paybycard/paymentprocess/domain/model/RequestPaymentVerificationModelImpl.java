package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.dto.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.exception.EmptyReferenceNumberException;
import com.internship.paybycard.paymentprocess.core.domain.exception.PaymentNotFoundException;
import com.internship.paybycard.paymentprocess.core.domain.model.RequestPaymentVerificationModel;
import com.internship.paybycard.paymentprocess.core.infrastructure.EmailService;
import com.internship.paybycard.paymentprocess.core.infrastructure.OtpService;
import com.internship.paybycard.paymentprocess.core.infrastructure.PaymentDao;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestPaymentVerificationModelImpl implements RequestPaymentVerificationModel {

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
            emailService.sendOtpEmail(paymentDto.getClientEmail(), referenceNumber, otpService.getOtp(referenceNumber));
        } else throw new PaymentNotFoundException("payment record doesn't exist");
    }

}
