package com.internship.paybycard.cardmanagement.application.interactors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateCardRequest implements CardDto {

    @NotBlank
    private String cvv;
    @NotBlank
    private String cardNumber;
    @NotBlank
    private String clientEmail;
    @NotBlank
    private String clientName;
    @NotNull
    private BigDecimal balance;
    @NotBlank
    private LocalDate expiryDate;


    @Override
    public boolean isNull() {
        return false;
    }
}
