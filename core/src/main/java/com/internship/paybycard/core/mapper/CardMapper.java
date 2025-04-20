package com.internship.paybycard.core.mapper;

public interface CardMapper<R,M> {
     R mapToModel(M card);

     M reverseMap(R reverseCard);
//     todo Rename both methods, u shouldn't be coupled with specific class since this is interface for all mappers
}
