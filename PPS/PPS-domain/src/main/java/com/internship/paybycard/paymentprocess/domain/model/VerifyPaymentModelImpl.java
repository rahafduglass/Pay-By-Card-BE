package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.exception.BusinessException;
import com.internship.paybycard.paymentprocess.core.domain.exception.PaymentNotFoundException;
import com.internship.paybycard.paymentprocess.core.domain.model.VerifyPaymentModel;
import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.core.domain.result.Status;
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
    private ErrorCode errorCode;

    @Override
    public void verifyPayment() {
        try {
            log.info("Verifying payment with reference number {}", referenceNumber);
            paymentDto = paymentDao.findPaymentByReferenceNumber(referenceNumber);
            if (paymentDto.isNull()) {
                log.error("Payment with reference number {} not found", referenceNumber);
                throw new PaymentNotFoundException("payment not found " + referenceNumber, ErrorCode.PAYMENT_NOT_FOUND);
            }
            isVerified = true;
        } catch (BusinessException e) {
            log.error("Business exception", e);
            errorCode = e.getErrorCode();
        } catch (Exception e) {
            log.error("unexpected error in verifyPayment(): {}", e.getMessage(), e);
            errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        }
    }

    @Override
    public void sendOtp() {
        try {
            if (isVerified) {
                log.info("Sending OTP with reference number= {}", referenceNumber);
                log.debug("generating OTP for reference number= {}", referenceNumber);
                String otp = otpService.generateOtp();
                log.debug("storing OTP for reference number= {}", referenceNumber);
                otpService.storeOtp(referenceNumber, otp);
                log.debug("sending OTP to email address= {}", paymentDto.getClientEmail());
                emailService.sendOtpEmail(paymentDto.getClientEmail(), referenceNumber, otp);
                errorCode = ErrorCode.SUCCESS;
            }
        } catch (Exception e) {
            log.error("unexpected error in verifyPayment(): {}", e.getMessage(), e);
            errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        }
    }

    @Override
    public Result<Void> result() {
        if (errorCode.equals(ErrorCode.SUCCESS)) {
            return new Result<>(Status.ACT, ErrorCode.SUCCESS, null);
        } else {
            return new Result<>(Status.RJC, errorCode, null);
        }
    }

}
