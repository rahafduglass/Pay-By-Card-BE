package com.internship.paybycard.cardmanagement.application.dtos.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetCardResponse {

    private String cardNumber;

    private String cvv;

    private LocalDate expiryDate;

}
