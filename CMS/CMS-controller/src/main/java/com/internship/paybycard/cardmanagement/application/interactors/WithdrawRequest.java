package com.internship.paybycard.cardmanagement.application.interactors;

import com.internship.paybycard.cardmanagement.core.interactor.WithdrawInteractor;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawRequest implements WithdrawInteractor {
    @NotNull
    private BigDecimal amount;
    @NotNull
    private ValidateCardRequest validateCardInfo;
}
