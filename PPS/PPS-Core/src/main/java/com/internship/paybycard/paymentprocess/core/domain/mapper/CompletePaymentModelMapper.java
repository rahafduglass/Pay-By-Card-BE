package com.internship.paybycard.paymentprocess.core.domain.mapper;

import com.internship.paybycard.paymentprocess.core.domain.command.CompletePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.model.CompletePaymentModel;

public interface CompletePaymentModelMapper {
    CompletePaymentModel commandToMode(CompletePaymentCommand command);
}
