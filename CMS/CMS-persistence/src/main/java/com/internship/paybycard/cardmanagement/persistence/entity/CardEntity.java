package com.internship.paybycard.cardmanagement.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String cardNumber;

    @Column(nullable = false)
    String CVV;

    @Column(nullable = false)
    String clientName;

    @Column(nullable = false)
    String clientEmail;

    @Column(nullable = false)
    LocalDate expiryDate;

    @Column(nullable = false)
    BigDecimal balance;
}
