package com.internship.paybycard.core.service;

public interface CardService<M> {
    boolean createCard(M cardModel);
    void updateCard(M cardModel);
    boolean validateCard(M cardModel);
}
