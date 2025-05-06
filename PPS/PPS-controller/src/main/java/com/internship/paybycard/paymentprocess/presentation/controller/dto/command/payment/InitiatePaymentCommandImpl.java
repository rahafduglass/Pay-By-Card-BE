package com.internship.paybycard.paymentprocess.presentation.controller.dto.command.payment;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.InitiatePaymentCommand;
import com.internship.paybycard.paymentprocess.presentation.controller.dto.command.card.VerifyCardCommandImpl;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Data
@Component
public class InitiatePaymentCommandImpl implements InitiatePaymentCommand {
    private  String items;
    private BigDecimal amount;
    private String clientName;
    private VerifyCardCommandImpl card;
}
