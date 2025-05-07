package com.internship.paybycard.paymentprocess.core.integration.cms.dto;

import java.time.LocalDate;

public interface VerifyCardDto {
    String getCardNumber();
    String getCVV();
    LocalDate getExpiryDate();


}
