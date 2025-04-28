package com.internship.paybycard.cardmanagement.application.interactors;


import com.internship.paybycard.cardmanagement.core.interactor.CreateCardInteractor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateCardRequest implements CreateCardInteractor {

    @NotBlank
    private String clientName;

    @NotBlank
    private String clientEmail;

    @NotNull
    private BigDecimal balance;

}
