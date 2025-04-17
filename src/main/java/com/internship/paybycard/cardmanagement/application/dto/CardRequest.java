package com.internship.paybycard.cardmanagement.application.dto;

import lombok.Data;

@Data
public class CardRequest {

    String clientName;
    String clientEmail;
    Double balance;
}
