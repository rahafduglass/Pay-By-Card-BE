package com.internship.paybycard.core.mapper;

public interface CardMapper<D,M> {
     D mapTo(M card);

     M reverseTo(D reverseCard);
}
