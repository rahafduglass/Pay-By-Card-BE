package com.internship.paybycard.paymentprocess.terminalui.dto.response;

import com.internship.paybycard.paymentprocess.core.domain.dto.card.VerifyCardDtoImpl;
import lombok.Data;

@Data
public class InitiatePaymentHandlerResponse {
    private String paymentReferenceNumber;
    private VerifyCardDtoImpl card;
}
