package com.internship.paybycard.paymentprocess.domain.mapper;

import com.internship.paybycard.paymentprocess.core.domain.dto.command.payment.VerifyPaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.mapper.payment.VerifyPaymentModelMapper;
import com.internship.paybycard.paymentprocess.core.domain.model.VerifyPaymentModel;
import com.internship.paybycard.paymentprocess.core.integration.EmailService;
import com.internship.paybycard.paymentprocess.core.integration.OtpService;
import com.internship.paybycard.paymentprocess.core.persistence.PaymentDao;
import com.internship.paybycard.paymentprocess.domain.model.VerifyPaymentModelImpl;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VerifyPaymentModelMapperImpl implements VerifyPaymentModelMapper {


    private final PaymentDao paymentDao;
    private final OtpService otpService;
    private final EmailService emailService;


    @Override
    public VerifyPaymentModel commandToModel(VerifyPaymentCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("command cannot be null");
        }
        return new VerifyPaymentModelImpl(command.getPaymentReference(), paymentDao, otpService, emailService);
    }
}
