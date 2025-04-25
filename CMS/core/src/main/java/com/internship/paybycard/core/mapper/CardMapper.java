package com.internship.paybycard.core.mapper;

public interface CardMapper<R,M> {
     R mapTo(M card);

     M reverseTo(R reverseCard);
}
