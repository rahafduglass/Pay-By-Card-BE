package com.internship.paybycard.paymentprocess.core.domain.mapper;

import com.internship.paybycard.paymentprocess.core.domain.command.RequestPaymentVerificationCommand;
import com.internship.paybycard.paymentprocess.core.domain.model.RequestPaymentVerificationModel;

public interface RequestPaymentVerificationModelMapper {
    RequestPaymentVerificationModel commandToModel(RequestPaymentVerificationCommand command);
}
