package com.internship.paybycard.core.service;

public interface CardService<M> {
    boolean createCard(M cardModel);
}
