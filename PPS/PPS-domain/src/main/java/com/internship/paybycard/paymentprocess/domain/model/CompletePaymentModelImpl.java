package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.exception.*;
import com.internship.paybycard.paymentprocess.core.domain.model.CompletePaymentModel;
import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.integration.OtpService;
import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;
import com.internship.paybycard.paymentprocess.core.persistence.PaymentDao;
import com.internship.paybycard.paymentprocess.core.integration.cms.service.CmsApiHandler;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
public class CompletePaymentModelImpl implements CompletePaymentModel {
    private final String referenceNumber;
    private final String OTP;
    private final VerifyCardDto verifyCardDto;
    private boolean isOtpVerified=false;

    private final OtpService otpService;
    private final CmsApiHandler cmsApiHandler;
    private final PaymentDao paymentDao;

    @Override
    public void verifyOTP() {
        boolean result = otpService.verifyOtp(referenceNumber, OTP);
        if (!result) {
            throw new InvalidOtpException("invalid or expired Otp",ErrorCode.INVALID_OR_EXPIRED_OTP);
        }
        isOtpVerified=true;
    }

    @Override
    public void pay() {
        if(isOtpVerified) {
            PaymentDto paymentDto=paymentDao.findPaymentByReferenceNumber(referenceNumber);
            if(paymentDto.isNull()) throw new PaymentNotFoundException("Payment not found",ErrorCode.PAYMENT_NOT_FOUND);
            cmsApiHandler.pay(verifyCardDto, paymentDto.getAmount());
            if(!(paymentDao.updatePaymentConfirmedByReferenceNumber(referenceNumber,true)==1))
                throw new PersistenceException("couldn't update card",ErrorCode.INTERNAL_SERVER_ERROR);
        }else throw new InvalidOtpException("invalid or expired Otp OR consider calling verifyOTP() first", ErrorCode.INVALID_OR_EXPIRED_OTP);
    }
}
