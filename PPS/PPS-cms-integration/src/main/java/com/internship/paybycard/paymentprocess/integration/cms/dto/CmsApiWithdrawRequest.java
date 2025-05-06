package com.internship.paybycard.paymentprocess.integration.cms.dto;

import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CmsApiWithdrawRequest {
    private VerifyCardDto validateCardInfo;
    private BigDecimal amount;
    public CmsApiWithdrawRequest(VerifyCardDto verifyCardDto, BigDecimal amount) {
        this.validateCardInfo = verifyCardDto;
        this.amount = amount;
    }
}
