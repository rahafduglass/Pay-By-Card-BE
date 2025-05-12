package com.internship.paybycard.paymentprocess.integration.cms.dto;

import com.internship.paybycard.paymentprocess.core.integration.cms.model.CardDto;
import lombok.Getter;

@Getter
public class CardApiResponse {
    private String status;
    private String errorCode;
    private CardDto data;
}
