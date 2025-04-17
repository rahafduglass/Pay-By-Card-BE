package com.internship.paybycard.cardmanagement.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Entity()
@Data
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
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
    Double balance;
}
