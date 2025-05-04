package com.internship.paybycard.paymentprocess.core.domain.dto.command.payment;

public interface CompletePaymentCommand {
    String getPaymentReference();
    String getOTP();


}
