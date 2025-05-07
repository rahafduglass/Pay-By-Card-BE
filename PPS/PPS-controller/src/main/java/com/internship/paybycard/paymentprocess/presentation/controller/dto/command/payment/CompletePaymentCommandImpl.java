package com.internship.paybycard.paymentprocess.presentation.controller.dto.command.payment;

import com.internship.paybycard.paymentprocess.core.domain.dto.card.VerifyCardDtoImpl;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.CompletePaymentCommand;
import lombok.Data;

import java.math.BigDecimal;

@Data
//todo Add validation rules (Mandatory/Optional | Maximum Length as well)

public class CompletePaymentCommandImpl implements CompletePaymentCommand {
    String paymentReference;
    String OTP;
    VerifyCardDtoImpl verifyCard;
}
