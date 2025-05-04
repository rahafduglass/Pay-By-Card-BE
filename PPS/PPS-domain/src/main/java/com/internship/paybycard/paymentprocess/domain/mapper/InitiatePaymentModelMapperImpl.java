package com.internship.paybycard.paymentprocess.domain.mapper;

import com.internship.paybycard.paymentprocess.core.domain.dto.command.payment.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.mapper.payment.InitiatePaymentModelMapper;
import com.internship.paybycard.paymentprocess.core.domain.model.InitiatePaymentModel;
import com.internship.paybycard.paymentprocess.core.infrastructure.PaymentDao;
import com.internship.paybycard.paymentprocess.core.domain.mapper.card.VerifyCardMapper;
import com.internship.paybycard.paymentprocess.core.infrastructure.cms.service.CmsApiHandler;
import com.internship.paybycard.paymentprocess.domain.model.InitiatePaymentModelImpl;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class InitiatePaymentModelMapperImpl implements InitiatePaymentModelMapper {

    private final PaymentDao paymentDao;
    private final CmsApiHandler cmsApiHandler;

    private final VerifyCardMapper verifyCardMapper;

    @Override
    public InitiatePaymentModel commandToModel(InitiatePaymentCommand command) {
        if(command==null){
            throw new IllegalArgumentException("command cannot be null");
        }
        return new InitiatePaymentModelImpl(command.getItems(), command.getAmount(), command.getClientName(), verifyCardMapper.commandToDto(command.getCard()), paymentDao, cmsApiHandler);
    }

}
