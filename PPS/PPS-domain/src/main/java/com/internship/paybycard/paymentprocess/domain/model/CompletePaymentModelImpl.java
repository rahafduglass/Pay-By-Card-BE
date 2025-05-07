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

public class CompletePaymentModelImpl implements CompletePaymentModel {
    private final String referenceNumber;
    private final String OTP;
    private final VerifyCardDto verifyCardDto;
    private boolean isOtpVerified = false;

    private final OtpService otpService;
    private final CmsApiHandler cmsApiHandler;
    private final PaymentDao paymentDao;

    @Override
    public void verifyOTP() {
        boolean result = otpService.verifyOtp(referenceNumber, OTP);
        if (!result) {
            throw new InvalidOtpException("invalid or expired Otp", ErrorCode.INVALID_OR_EXPIRED_OTP);
        }
        isOtpVerified = true;
    }

    @Override
    public void pay() {
        if (isOtpVerified) {
            PaymentDto paymentDto = paymentDao.findPaymentByReferenceNumber(referenceNumber);
            if (paymentDto.isNull())
                throw new PaymentNotFoundException("Payment not found", ErrorCode.PAYMENT_NOT_FOUND);
            cmsApiHandler.pay(verifyCardDto, paymentDto.getAmount());
            if (!(paymentDao.updatePaymentConfirmedByReferenceNumber(referenceNumber, true) == 1))
                throw new PersistenceException("couldn't update card", ErrorCode.INTERNAL_SERVER_ERROR);
        } else
            throw new InvalidOtpException("invalid or expired Otp OR consider calling verifyOTP() first", ErrorCode.INVALID_OR_EXPIRED_OTP);
    }

    public static ModelBuilder builder() {
        return new ModelBuilder();
    }

    private CompletePaymentModelImpl(String referenceNumber, String OTP, VerifyCardDto verifyCardDto, OtpService otpService, CmsApiHandler cmsApiHandler, PaymentDao paymentDao) {
        this.referenceNumber = referenceNumber;
        this.OTP = OTP;
        this.verifyCardDto = verifyCardDto;
        this.otpService = otpService;
        this.cmsApiHandler = cmsApiHandler;
        this.paymentDao = paymentDao;
    }

    public static class ModelBuilder {
        private String referenceNumber;
        private String OTP;
        private VerifyCardDto verifyCardDto;

        private OtpService otpService;
        private CmsApiHandler cmsApiHandler;
        private PaymentDao paymentDao;

        public ModelBuilder referenceNumber(String referenceNumber) {
            this.referenceNumber = referenceNumber;
            return this;
        }

        public ModelBuilder OTP(String OTP) {
            this.OTP = OTP;
            return this;
        }

        public ModelBuilder verifyCardDto(VerifyCardDto verifyCardDto) {
            this.verifyCardDto = verifyCardDto;
            return this;
        }

        public ModelBuilder otpService(OtpService otpService) {
            this.otpService = otpService;
            return this;
        }

        public ModelBuilder cmsApiHandler(CmsApiHandler cmsApiHandler) {
            this.cmsApiHandler = cmsApiHandler;
            return this;
        }

        public ModelBuilder paymentDao(PaymentDao paymentDao) {
            this.paymentDao = paymentDao;
            return this;
        }

        public CompletePaymentModel build() {
            return new CompletePaymentModelImpl(this.referenceNumber, this.OTP, this.verifyCardDto, this.otpService, this.cmsApiHandler, this.paymentDao);
        }
    }
}
