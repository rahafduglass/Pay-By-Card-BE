package com.internship.paybycard.paymentprocess.core.domain.mapper;

import com.internship.paybycard.paymentprocess.core.domain.dto.command.CompletePaymentCommand;
import com.internship.paybycard.paymentprocess.core.domain.model.CompletePaymentModel;

public interface CompletePaymentModelMapper {
    CompletePaymentModel commandToModel(CompletePaymentCommand command);
}
