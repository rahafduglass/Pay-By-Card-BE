package com.internship.paybycard.paymentprocess;

import com.internship.paybycard.paymentprocess.core.domain.mapper.card.VerifyCardMapper;
import com.internship.paybycard.paymentprocess.core.domain.mapper.payment.CompletePaymentModelMapper;
import com.internship.paybycard.paymentprocess.core.domain.mapper.payment.InitiatePaymentModelMapper;
import com.internship.paybycard.paymentprocess.core.domain.mapper.payment.VerifyPaymentModelMapper;
import com.internship.paybycard.paymentprocess.core.domain.usecase.PaymentProcessUseCase;
import com.internship.paybycard.paymentprocess.core.integration.EmailService;
import com.internship.paybycard.paymentprocess.core.integration.OtpService;
import com.internship.paybycard.paymentprocess.core.persistence.PaymentDao;
import com.internship.paybycard.paymentprocess.core.integration.cms.service.CmsApiHandler;
import com.internship.paybycard.paymentprocess.domain.mapper.CompletePaymentModelMapperImpl;
import com.internship.paybycard.paymentprocess.domain.mapper.InitiatePaymentModelMapperImpl;
import com.internship.paybycard.paymentprocess.domain.mapper.VerifyPaymentModelMapperImpl;
import com.internship.paybycard.paymentprocess.domain.usecase.PaymentProcessUseCaseImpl;
import com.internship.paybycard.paymentprocess.integration.cms.config.CmsApiProperties;
import com.internship.paybycard.paymentprocess.integration.cms.service.CmsApiHandlerRestTemplateImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeansConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public InitiatePaymentModelMapper initiatePaymentModelMapper(PaymentDao paymentDao, CmsApiHandler cmsApiHandler, VerifyCardMapper verifyCardMapper) {
        return new InitiatePaymentModelMapperImpl(paymentDao, cmsApiHandler, verifyCardMapper);
    }

    @Bean
    public PaymentProcessUseCase paymentProcessUseCase(InitiatePaymentModelMapper initiatePaymentModelMapper, VerifyPaymentModelMapper verifyPaymentModelMapper, CompletePaymentModelMapper completePaymentModelMapper) {
        return new PaymentProcessUseCaseImpl(initiatePaymentModelMapper, verifyPaymentModelMapper, completePaymentModelMapper);
    }


    @Bean
    public VerifyPaymentModelMapper verifyPaymentModelMapper(PaymentDao paymentDao, OtpService otpService, EmailService emailService) {
        return new VerifyPaymentModelMapperImpl(paymentDao, otpService, emailService);
    }

    @Bean
    public CompletePaymentModelMapper completePaymentModelMapper(PaymentDao paymentDao, OtpService otpService, CmsApiHandler cmsApiHandler) {
        return new CompletePaymentModelMapperImpl(paymentDao, otpService, cmsApiHandler);
    }

    @Bean
    public CmsApiHandler cmsApiHandler(CmsApiProperties cmsApiProperties) {
        return new CmsApiHandlerRestTemplateImpl(cmsApiProperties);
    }
}
