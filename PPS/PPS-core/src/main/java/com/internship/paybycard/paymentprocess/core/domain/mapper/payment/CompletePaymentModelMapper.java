package com.internship.paybycard.paymentprocess.core.domain.mapper.payment;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.CompletePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.model.CompletePaymentModel;

public interface CompletePaymentModelMapper {
    CompletePaymentModel commandToModel(CompletePaymentCommand command);
}
