package com.internship.paybycard.paymentprocess.core.domain.mapper.payment;

import com.internship.paybycard.paymentprocess.core.domain.dto.command.payment.RequestPaymentVerificationCommand;
import com.internship.paybycard.paymentprocess.core.domain.model.VerifyPaymentModel;

public interface RequestPaymentVerificationModelMapper {
    VerifyPaymentModel commandToModel(RequestPaymentVerificationCommand command);
}
