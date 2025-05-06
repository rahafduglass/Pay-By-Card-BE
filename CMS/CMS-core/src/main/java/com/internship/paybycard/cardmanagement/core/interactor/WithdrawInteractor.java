package com.internship.paybycard.cardmanagement.core.interactor;

import java.math.BigDecimal;

public interface WithdrawInteractor {

    BigDecimal getAmount();

    ValidateCardInteractor getValidateCardInfo();
}
