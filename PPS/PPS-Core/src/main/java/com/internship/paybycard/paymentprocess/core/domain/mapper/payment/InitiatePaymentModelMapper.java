package com.internship.paybycard.paymentprocess.core.domain.mapper.payment;

import com.internship.paybycard.paymentprocess.core.domain.dto.command.payment.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.model.InitiatePaymentModel;

public interface InitiatePaymentModelMapper {
    InitiatePaymentModel commandToModel(InitiatePaymentCommand command);
}
