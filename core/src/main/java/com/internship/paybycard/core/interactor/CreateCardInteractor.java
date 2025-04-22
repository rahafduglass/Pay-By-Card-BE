package com.internship.paybycard.core.interactor;

import java.math.BigDecimal;


public interface CreateCardInteractor {
    String getClientEmail();
    String getClientName();
    BigDecimal getBalance();

    void setClientEmail(String clientEmail);
    void setClientName(String clientName);
    void setBalance(BigDecimal balance);


}
