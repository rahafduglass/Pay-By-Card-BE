package com.internship.paybycard.paymentprocess.domain.mapper;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.CompletePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.mapper.payment.CompletePaymentModelMapper;
import com.internship.paybycard.paymentprocess.core.domain.model.CompletePaymentModel;
import com.internship.paybycard.paymentprocess.core.integration.OtpService;
import com.internship.paybycard.paymentprocess.core.integration.cms.service.CmsApiHandler;
import com.internship.paybycard.paymentprocess.core.persistence.PaymentDao;
import com.internship.paybycard.paymentprocess.domain.model.CompletePaymentModelImpl;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CompletePaymentModelMapperImpl implements CompletePaymentModelMapper {

    private final PaymentDao paymentDao;
    private final OtpService otpService;
    private final CmsApiHandler cmsApiHandler;

    @Override
    public CompletePaymentModel commandToModel(CompletePaymentCommand command) {
        if (command == null)
            throw new IllegalArgumentException("command cannot be null");
        return CompletePaymentModelImpl.builder()
                .OTP(command.getOTP())
                .referenceNumber(command.getPaymentReference())
                .verifyCardDto(command.getVerifyCard())
                .cmsApiHandler(cmsApiHandler)
                .otpService(otpService)
                .paymentDao(paymentDao)
                .build();
    }
}
