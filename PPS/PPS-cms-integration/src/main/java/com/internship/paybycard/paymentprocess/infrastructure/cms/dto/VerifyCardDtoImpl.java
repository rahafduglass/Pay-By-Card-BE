package com.internship.paybycard.paymentprocess.infrastructure.cms.dto;

import com.internship.paybycard.paymentprocess.core.infrastructure.cms.dto.VerifyCardDto;
import lombok.Data;

import java.time.LocalDate;


@Data
public class VerifyCardDtoImpl implements VerifyCardDto {
    private String cardNumber;
    private String CVV;
    private LocalDate expiryDate;
}
