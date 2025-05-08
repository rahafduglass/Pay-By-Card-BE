package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.exception.InvalidOtpException;
import com.internship.paybycard.paymentprocess.core.domain.exception.PaymentNotFoundException;
import com.internship.paybycard.paymentprocess.core.domain.exception.PersistenceException;
import com.internship.paybycard.paymentprocess.core.domain.model.CompletePaymentModel;
import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.integration.OtpService;
import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;
import com.internship.paybycard.paymentprocess.core.integration.cms.service.CmsApiHandler;
import com.internship.paybycard.paymentprocess.core.persistence.PaymentDao;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class CompletePaymentModelImpl implements CompletePaymentModel {
    private final String referenceNumber;
    private final String OTP;
    private final VerifyCardDto verifyCardDto;
    private final OtpService otpService;
    private final CmsApiHandler cmsApiHandler;
    private final PaymentDao paymentDao;

    private boolean isOtpVerified = false;

    @Builder
    public CompletePaymentModelImpl(String referenceNumber, String OTP, VerifyCardDto verifyCardDto, OtpService otpService, CmsApiHandler cmsApiHandler, PaymentDao paymentDao) {
        this.referenceNumber = referenceNumber;
        this.OTP = OTP;
        this.verifyCardDto = verifyCardDto;
        this.otpService = otpService;
        this.cmsApiHandler = cmsApiHandler;
        this.paymentDao = paymentDao;
    }

    @Override
    public void verifyOTP() {
        log.info("Verifying OTP for reference number {}", referenceNumber);
        boolean result = otpService.verifyOtp(referenceNumber, OTP);
        if (!result) {
            log.debug("Failed to verify OTP for reference number {} {}", referenceNumber, " invalid or expired Otp");
            throw new InvalidOtpException("invalid or expired Otp", ErrorCode.INVALID_OR_EXPIRED_OTP);
        }
        log.debug("successful verification, valid OTP for reference number {}", referenceNumber);
        isOtpVerified = true;
    }

    @Override
    public void pay() {
        if (isOtpVerified) {
            log.info("performing payment for payment with reference number {}", referenceNumber);
            PaymentDto retrievedPayment = paymentDao.findPaymentByReferenceNumber(referenceNumber);
            if (retrievedPayment.isNull()) {
                log.debug("payment with reference number {} is null -> not found", referenceNumber);
                throw new PaymentNotFoundException("Payment not found", ErrorCode.PAYMENT_NOT_FOUND);
            }
            cmsApiHandler.pay(verifyCardDto, retrievedPayment.getAmount());
            if (!(paymentDao.updatePaymentConfirmedByReferenceNumber(referenceNumber, true) == 1)) {
                log.error("Failed to perform payment by reference number {} in pay()", referenceNumber);
                throw new PersistenceException("couldn't update card", ErrorCode.INTERNAL_SERVER_ERROR);
            }
        } else {
            log.debug("payment with reference number {} is not verified yet", referenceNumber);
            throw new InvalidOtpException("invalid or expired Otp OR consider calling verifyOTP() first", ErrorCode.INVALID_OR_EXPIRED_OTP);
        }
    }

}
