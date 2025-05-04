package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.exception.EmptyOtpException;
import com.internship.paybycard.paymentprocess.core.domain.exception.EmptyReferenceNumberException;
import com.internship.paybycard.paymentprocess.core.domain.model.CompletePaymentModel;
import com.internship.paybycard.paymentprocess.core.infrastructure.OtpService;
import com.internship.paybycard.paymentprocess.core.infrastructure.PaymentDao;
import com.internship.paybycard.paymentprocess.core.infrastructure.cms.service.CmsApiHandler;
import lombok.Data;

@Data
public class CompletePaymentModelImpl implements CompletePaymentModel {
    private String referenceNumber;
    private String OTP;

    private final OtpService otpService;
    private final CmsApiHandler cmsApiHandler;
    private final PaymentDao paymentDao;

    @Override
    public void verifyOTP() {
        if (referenceNumber == null || referenceNumber.isEmpty()) {
            throw new EmptyReferenceNumberException("reference number is empty");
        } else if (OTP == null || OTP.isEmpty()) throw new EmptyOtpException("OTP is empty");

    }

    @Override
    public void process() {
        //do payment -> cms
        //update to confirmed -> dao
    }
}
