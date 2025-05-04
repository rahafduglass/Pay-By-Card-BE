package com.internship.paybycard.paymentprocess.infrastructure.cms.dto;

import com.internship.paybycard.paymentprocess.core.infrastructure.cms.model.CardDto;

public class CardApiResponse {
    private String status;
    private String errorCode;
    private CardDto data;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }

    public CardDto getData() { return data; }
    public void setData(CardDto data) { this.data = data; }

}
