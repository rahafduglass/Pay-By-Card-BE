package com.internship.paybycard.paymentprocess.domain.mapper;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.mapper.payment.InitiatePaymentModelMapper;
import com.internship.paybycard.paymentprocess.core.domain.model.InitiatePaymentModel;
import com.internship.paybycard.paymentprocess.core.persistence.PaymentDao;
import com.internship.paybycard.paymentprocess.core.domain.mapper.card.VerifyCardMapper;
import com.internship.paybycard.paymentprocess.core.integration.cms.service.CmsApiHandler;
import com.internship.paybycard.paymentprocess.domain.model.InitiatePaymentModelImpl;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class InitiatePaymentModelMapperImpl implements InitiatePaymentModelMapper {

    private final PaymentDao paymentDao;
    private final CmsApiHandler cmsApiHandler;

    private final VerifyCardMapper verifyCardMapper;

    @Override
    public InitiatePaymentModel commandToModel(InitiatePaymentCommand command) {
        if (command == null || command.getCard() == null) {
            throw new IllegalArgumentException("command cannot be null");
        }
        return InitiatePaymentModelImpl.builder()
                .items(command.getItems())
                .amount(command.getAmount())
                .clientName(command.getClientName())
                .card(verifyCardMapper.commandToDto(command.getCard()))
                .paymentDao(paymentDao)
                .cmsApiHandler(cmsApiHandler)
                .build();
    }

}
