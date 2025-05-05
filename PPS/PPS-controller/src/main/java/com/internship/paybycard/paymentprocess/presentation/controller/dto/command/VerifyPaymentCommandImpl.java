package com.internship.paybycard.paymentprocess.presentation.controller.dto.command;

import com.internship.paybycard.paymentprocess.core.domain.dto.command.payment.VerifyPaymentCommand;
import lombok.Data;

@Data
public class VerifyPaymentCommandImpl implements VerifyPaymentCommand {

    private String paymentReference;

}
