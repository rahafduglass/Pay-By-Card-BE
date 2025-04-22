package com.internship.paybycard.cardmanagement.application.interactors;

import com.internship.paybycard.core.interactor.ValidateCardInteractor;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Data
public class ValidateCardRequest implements ValidateCardInteractor {

    @NotBlank
    private String cardNumber;
    @NotNull
    private LocalDate expiryDate;
    @NotBlank
    private String CVV;
}
