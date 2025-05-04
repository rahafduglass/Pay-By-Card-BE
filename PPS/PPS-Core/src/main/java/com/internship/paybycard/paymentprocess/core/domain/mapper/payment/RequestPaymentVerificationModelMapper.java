package com.internship.paybycard.paymentprocess.core.domain.mapper.payment;

import com.internship.paybycard.paymentprocess.core.domain.dto.command.payment.RequestPaymentVerificationCommand;
import com.internship.paybycard.paymentprocess.core.domain.model.RequestPaymentVerificationModel;

public interface RequestPaymentVerificationModelMapper {
    RequestPaymentVerificationModel commandToModel(RequestPaymentVerificationCommand command);
}
