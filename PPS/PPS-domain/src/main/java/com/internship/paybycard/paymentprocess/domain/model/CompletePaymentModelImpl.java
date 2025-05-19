package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.exception.BusinessException;
import com.internship.paybycard.paymentprocess.core.domain.exception.InvalidOtpException;
import com.internship.paybycard.paymentprocess.core.domain.exception.PaymentNotFoundException;
import com.internship.paybycard.paymentprocess.core.domain.exception.PersistenceException;
import com.internship.paybycard.paymentprocess.core.domain.model.CompletePaymentModel;
import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.core.domain.result.Status;
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
    private ErrorCode errorCode;

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
        try {
            log.info("Verifying OTP for reference number {}", referenceNumber);
            if (!otpService.verifyOtp(referenceNumber, OTP)) {
                log.debug("Failed to verify OTP for reference number {} {}", referenceNumber, " invalid or expired Otp");
                throw new InvalidOtpException("invalid or expired Otp", ErrorCode.INVALID_OR_EXPIRED_OTP);
            }
            log.debug("successful verification, valid OTP for reference number {}", referenceNumber);
            isOtpVerified = true;
        } catch (BusinessException e) {
            log.error("Business exception", e);
            errorCode = e.getErrorCode();
        } catch (Exception e) {
            log.error("unexpected error in completePayment() : {}", e.getMessage(), e);
            errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        }
    }

    @Override
    public void pay() {
        try {
            if (isOtpVerified) {
                log.info("performing payment for payment with reference number {}", referenceNumber);
                PaymentDto retrievedPayment = paymentDao.findPaymentByReferenceNumber(referenceNumber);
                if (retrievedPayment.isNull()) {
                    log.debug("payment with reference number {} is null -> not found", referenceNumber);
                    throw new PaymentNotFoundException("Payment not found", ErrorCode.PAYMENT_NOT_FOUND);
                }
                cmsApiHandler.withdraw(verifyCardDto, retrievedPayment.getAmount());
                if (!(paymentDao.updatePaymentConfirmed(referenceNumber, true) == 1)) {
                    log.error("Failed to perform payment by reference number {} in pay()", referenceNumber);
                    throw new PersistenceException("couldn't update card", ErrorCode.INTERNAL_SERVER_ERROR);
                }
                errorCode = ErrorCode.SUCCESS;
            }
        } catch (BusinessException e) {
            log.error("Business exception", e);
            errorCode = e.getErrorCode();
        } catch (Exception e) {
            log.error("unexpected error in completePayment() : {}", e.getMessage(), e);
            errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        }
    }

    @Override
    public Result<Void> result() {
        if (errorCode == ErrorCode.SUCCESS) {
            return new Result<>(Status.ACT, ErrorCode.SUCCESS, null);
        } else
            return new Result<>(Status.RJC, errorCode, null);
    }
}
