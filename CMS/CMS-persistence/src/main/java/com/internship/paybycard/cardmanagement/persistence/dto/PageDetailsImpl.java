package com.internship.paybycard.cardmanagement.persistence.dto;

import com.internship.paybycard.cardmanagement.core.dao.PageDetails;
import com.internship.paybycard.cardmanagement.core.dto.CardDto;
import lombok.Data;

import java.util.List;

@Data
public class PageDetailsImpl implements PageDetails {
    List<CardDto> content;
    Long totalElements;
    int totalPages;
    int number;
    Long size;
    boolean isEmpty;
}
