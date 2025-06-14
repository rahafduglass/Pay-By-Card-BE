package com.internship.paybycard.paymentprocess.core.domain.mapper.payment;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.VerifyPaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.model.VerifyPaymentModel;

public interface VerifyPaymentModelMapper {
    VerifyPaymentModel commandToModel(VerifyPaymentCommand command);
}
