package com.internship.paybycard.cardmanagement.application.interactors;


import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CardRequest {
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @NotNull
    private String clientName;
    @NotNull
    private String clientEmail;
    @NotNull
    private BigDecimal balance;
}
