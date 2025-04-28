package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.exception.EmptyReferenceNumberException;
import com.internship.paybycard.paymentprocess.core.domain.exception.PaymentNotFoundException;
import com.internship.paybycard.paymentprocess.core.domain.model.PaymentModel;
import com.internship.paybycard.paymentprocess.core.domain.model.RequestPaymentVerificationModel;
import com.internship.paybycard.paymentprocess.core.infrastructure.EmailService;
import com.internship.paybycard.paymentprocess.core.infrastructure.OtpService;
import com.internship.paybycard.paymentprocess.core.infrastructure.PaymentDao;
import lombok.Data;

@Data
public class RequestPaymentVerificationModelImpl implements RequestPaymentVerificationModel {

    private String referenceNumber;
    private boolean isVerified = false;
    private PaymentModel paymentModel;

    private final PaymentDao paymentDao;
    private final OtpService otpService;
    private final EmailService emailService;


    @Override
    public void verifyPayment() {
        if(referenceNumber == null || referenceNumber.isEmpty()) {
            throw new EmptyReferenceNumberException("empty reference number");
        }
        paymentModel = paymentDao.findPaymentByReferenceNumber(referenceNumber);
        isVerified = true;
    }

    @Override
    public void process() {
        if (isVerified) {
            emailService.sendOtpEmail(paymentModel.getClientEmail(), referenceNumber, otpService.generateOtp(referenceNumber));
        }else throw new PaymentNotFoundException("payment record doesn't exist");
    }

}
