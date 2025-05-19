package com.internship.paybycard.paymentprocess.terminalui.dto.payment.command;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.VerifyPaymentCommand;
import lombok.Data;

@Data
public class VerifyPaymentCommandImpl implements VerifyPaymentCommand {

    private String paymentReference;
}
