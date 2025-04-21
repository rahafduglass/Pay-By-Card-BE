package com.internship.paybycard.cardmanagement.application.interactors;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CreateCardRequest implements CardDto{

    @NotNull
    @NotBlank
    private String clientName;

    @NotBlank
    @NotNull
    private String clientEmail;


    @NotNull
    private BigDecimal balance;

    @Override
    public boolean isNull() {
        return false;
    }
}
