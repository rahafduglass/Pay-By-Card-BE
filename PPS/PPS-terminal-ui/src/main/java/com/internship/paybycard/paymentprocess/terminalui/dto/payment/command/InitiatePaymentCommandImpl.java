package com.internship.paybycard.paymentprocess.terminalui.dto.payment.command;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.terminalui.dto.card.VerifyCardDetailsImpl;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InitiatePaymentCommandImpl implements InitiatePaymentCommand {
    private String items;

    private BigDecimal amount;

    private String clientName;

    private VerifyCardDetailsImpl card;
}
