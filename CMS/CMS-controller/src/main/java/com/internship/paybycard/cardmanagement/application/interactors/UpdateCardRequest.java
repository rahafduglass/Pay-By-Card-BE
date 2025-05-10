package com.internship.paybycard.cardmanagement.application.interactors;

import com.internship.paybycard.cardmanagement.core.interactor.UpdateCardInteractor;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateCardRequest implements UpdateCardInteractor {


    @NotBlank
    @Size(min=3,max=3)
    private String cvv;

    @NotBlank
    @Size(min=36,max=36)
    private String cardNumber;

    @NotBlank
    private String clientEmail;

    @NotBlank
    private String clientName;

    @NotNull
    @DecimalMin(value = "1")
    private BigDecimal balance;

    @NotNull
    private LocalDate expiryDate;

}
