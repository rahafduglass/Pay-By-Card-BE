package com.internship.paybycard.core.mapper;

public interface CardMapper<R,M> {
     R mapToModel(M card);

     M reverseMap(R reverseCard);
}
