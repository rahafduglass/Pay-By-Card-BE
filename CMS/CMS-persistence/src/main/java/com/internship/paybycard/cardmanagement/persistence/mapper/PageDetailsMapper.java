package com.internship.paybycard.cardmanagement.persistence.mapper;

import com.internship.paybycard.cardmanagement.core.dao.PageDetails;
import com.internship.paybycard.cardmanagement.persistence.dto.PageDetailsImpl;
import com.internship.paybycard.cardmanagement.persistence.entity.CardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PageDetailsMapper {
    private final CardEntityMapperImpl cardEntityMapperImpl;

    public PageDetails toPaginatedDtos(Page<CardEntity> paginatedEntities) {
        PageDetailsImpl paginatedDtos = new PageDetailsImpl();
        paginatedDtos.setTotalElements(paginatedEntities.getTotalElements());
        paginatedDtos.setContent((paginatedEntities.getContent()).stream().map(cardEntityMapperImpl::reverseTo).collect(Collectors.toList()));
        paginatedDtos.setTotalPages(paginatedEntities.getTotalPages());
        paginatedDtos.setSize(paginatedDtos.getSize());
        paginatedDtos.setNumber(paginatedEntities.getNumber());
        paginatedDtos.setEmpty(paginatedEntities.isEmpty());
        return paginatedDtos;
    }
}
