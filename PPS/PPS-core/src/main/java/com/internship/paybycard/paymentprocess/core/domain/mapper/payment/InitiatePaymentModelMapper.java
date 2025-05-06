package com.internship.paybycard.paymentprocess.core.domain.mapper.payment;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.model.InitiatePaymentModel;

public interface InitiatePaymentModelMapper {
    InitiatePaymentModel commandToModel(InitiatePaymentCommand command);
}
