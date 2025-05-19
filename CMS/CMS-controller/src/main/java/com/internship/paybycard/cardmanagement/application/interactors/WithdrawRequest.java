package com.internship.paybycard.cardmanagement.application.interactors;

import com.internship.paybycard.cardmanagement.core.interactor.WithdrawInteractor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawRequest implements WithdrawInteractor {
    @NotNull
    @DecimalMin(value = "1")
    @DecimalMax(value = "10000.00")
    private BigDecimal amount;
    @NotNull
    @Valid
    private ValidateCardRequest validateCardInfo;
}
