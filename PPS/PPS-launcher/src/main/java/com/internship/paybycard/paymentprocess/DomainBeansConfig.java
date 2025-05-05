package com.internship.paybycard.paymentprocess;

import com.internship.paybycard.paymentprocess.core.domain.mapper.card.VerifyCardMapper;
import com.internship.paybycard.paymentprocess.core.domain.mapper.payment.InitiatePaymentModelMapper;
import com.internship.paybycard.paymentprocess.core.domain.mapper.payment.VerifyPaymentModelMapper;
import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import com.internship.paybycard.paymentprocess.core.integration.EmailService;
import com.internship.paybycard.paymentprocess.core.integration.OtpService;
import com.internship.paybycard.paymentprocess.core.persistence.PaymentDao;
import com.internship.paybycard.paymentprocess.core.integration.cms.service.CmsApiHandler;
import com.internship.paybycard.paymentprocess.domain.mapper.InitiatePaymentModelMapperImpl;
import com.internship.paybycard.paymentprocess.domain.mapper.VerifyPaymentModelMapperImpl;
import com.internship.paybycard.paymentprocess.domain.usecase.PaymentProcessUseCaseImpl;
import com.internship.paybycard.paymentprocess.integration.cms.mapper.VerifyCardMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainBeansConfig {

    @Bean
    public PaymentProcessUseCase paymentProcessUseCase(InitiatePaymentModelMapper  initiatePaymentModelMapper, VerifyPaymentModelMapper verifyPaymentModelMapper) {
        return new PaymentProcessUseCaseImpl(initiatePaymentModelMapper,verifyPaymentModelMapper);
    }

    @Bean
    public InitiatePaymentModelMapper initiatePaymentModelMapper(PaymentDao paymentDao, CmsApiHandler cmsApiHandler, VerifyCardMapper verifyCardMapper) {
        return new InitiatePaymentModelMapperImpl(paymentDao, cmsApiHandler,verifyCardMapper);
    }

    @Bean
    public VerifyPaymentModelMapper verifyPaymentModelMapper(PaymentDao paymentDao, OtpService otpService, EmailService emailService) {
        return new VerifyPaymentModelMapperImpl(paymentDao,otpService,emailService);
    }



}
