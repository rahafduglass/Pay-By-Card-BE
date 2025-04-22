package com.internship.paybycard.cardmanagement.application.interactors;

import com.internship.paybycard.core.interactor.UpdateCardInteractor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateCardRequest implements UpdateCardInteractor {


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

    @NotNull
    private LocalDate expiryDate;

}
