package com.internship.paybycard.cardmanagement.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity()
@Data
public class CardEntity {
    @Id
    String cardNumber;

    String CVV;
    String clientName;
    String clientEmail;
    LocalDate expiryDate;
    Double balance;
}
