package com.internship.paybycard.paymentprocess.presentation.controller.dto.command.payment;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.VerifyPaymentCommand;
import lombok.Data;

@Data
public class VerifyPaymentCommandImpl implements VerifyPaymentCommand {

    private String paymentReference;

}
