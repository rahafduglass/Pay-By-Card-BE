package com.internship.paybycard.paymentprocess.core.domain.dto.card;

import com.internship.paybycard.paymentprocess.core.integration.cms.dto.VerifyCardDto;
import lombok.Data;

import java.time.LocalDate;


@Data
public class VerifyCardDtoImpl implements VerifyCardDto {
    private String cardNumber;
    private String CVV;
    private LocalDate expiryDate;
}
