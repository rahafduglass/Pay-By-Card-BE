package com.internship.paybycard.paymentprocess.terminalui.dto.payment.command;

import com.internship.paybycard.paymentprocess.core.domain.dto.card.VerifyCardDtoImpl;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.command.CompletePaymentCommand;
import lombok.Data;

@Data
public class CompletePaymentCommandImpl implements CompletePaymentCommand {
    String paymentReference;
    String OTP;
    VerifyCardDtoImpl verifyCard;
}
