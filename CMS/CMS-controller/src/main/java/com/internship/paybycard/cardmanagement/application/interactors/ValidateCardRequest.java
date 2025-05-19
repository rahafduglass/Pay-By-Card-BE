package com.internship.paybycard.cardmanagement.application.interactors;

import com.internship.paybycard.cardmanagement.core.interactor.ValidateCardInteractor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ValidateCardRequest implements ValidateCardInteractor {

    @NotBlank
    @Size(min=36,max=36)
    private String cardNumber;
    @NotNull
    private LocalDate expiryDate;
    @NotBlank
    @Size(min=3,max=3)
    private String CVV;
}
