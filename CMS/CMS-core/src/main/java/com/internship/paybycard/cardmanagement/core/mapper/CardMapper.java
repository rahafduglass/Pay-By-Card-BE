package com.internship.paybycard.cardmanagement.core.mapper;

public interface CardMapper<R,M> {
     R reverseTo(M card);

     M mapTo(R reverseCard);
}
