package com.internship.paybycard.cardmanagement.application.interactors;

import com.internship.paybycard.core.interactor.ValidateCardInteractor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ValidateCardRequest implements ValidateCardInteractor {

    private String cardNumber;
    private LocalDate expiryDate;
    private String CVV;
}
