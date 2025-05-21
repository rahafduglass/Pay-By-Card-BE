package com.internship.paybycard.cardmanagement.core.dao;

import com.internship.paybycard.cardmanagement.core.dto.CardDto;

import java.util.List;

public interface PageDetails {
     List<CardDto> getContent();
     Long getTotalElements();
     int getTotalPages();
     Long getSize();
     int getNumber();
     boolean isEmpty();
}
