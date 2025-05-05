package com.internship.paybycard.paymentprocess.persistence;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class PaymentProcessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String referenceNumber;

    private String items;

    private BigDecimal amount;

    private String cardNumber;

    private String clientName;

    private String clientEmail;

    private Boolean confirmed;

}
