package com.internship.paybycard.cardmanagement.application.interactors;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ValidateCardRequest implements com.internship.paybycard.core.interactor.ValidateCardInteractor {

    private String cardNumber;
    private LocalDate expiryDate;
    private String CVV;
}
