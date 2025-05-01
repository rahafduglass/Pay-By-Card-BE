package com.internship.paybycard.paymentprocess.core.domain.mapper;

import com.internship.paybycard.paymentprocess.core.domain.dto.command.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.model.InitiatePaymentModel;

public interface InitiatePaymentModelMapper {
    InitiatePaymentModel commandToModel(InitiatePaymentCommand command);
}
