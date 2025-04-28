package com.internship.paybycard.paymentprocess.domain.model;

import com.internship.paybycard.paymentprocess.core.domain.model.PaymentModel;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class PaymentModelImpl implements PaymentModel {
    private Long paymentId;
    private String referenceNumber;
    private String items;
    private BigDecimal amount;
    private String cardNumber;
    private String clientName;
    private String clientEmail;
    private Boolean confirmed;
}
