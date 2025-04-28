package com.internship.paybycard.paymentprocess.domain.dto;

import com.internship.paybycard.paymentprocess.core.domain.dto.PaymentDto;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class PaymentDtoImpl implements PaymentDto {
    private Long paymentId;
    private String referenceNumber;
    private String items;
    private BigDecimal amount;
    private String cardNumber;
    private String clientName;
    private String clientEmail;
    private Boolean confirmed;
}
