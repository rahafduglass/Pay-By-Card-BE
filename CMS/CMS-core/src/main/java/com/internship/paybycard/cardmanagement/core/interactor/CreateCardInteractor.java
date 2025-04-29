package com.internship.paybycard.cardmanagement.core.interactor;

import java.math.BigDecimal;


public interface CreateCardInteractor {
    String getClientEmail();
    String getClientName();
    BigDecimal getBalance();

}
