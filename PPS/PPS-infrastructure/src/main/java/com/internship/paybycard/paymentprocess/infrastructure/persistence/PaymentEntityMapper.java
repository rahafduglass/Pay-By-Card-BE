package com.internship.paybycard.paymentprocess.infrastructure.persistence;

import com.internship.paybycard.paymentprocess.core.domain.dto.PaymentDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PaymentEntityMapper {

    public PaymentProcessEntity dtoToEntity(PaymentDto paymentDto) {
        if(paymentDto == null) {
            throw new RuntimeException("Payment DTO is null");
        }
        PaymentProcessEntity paymentProcessEntity = new PaymentProcessEntity();
        paymentProcessEntity.setId(paymentDto.getId());
        paymentProcessEntity.setReferenceNumber(paymentDto.getReferenceNumber());
        paymentProcessEntity.setItems(paymentDto.getItems());
        paymentProcessEntity.setAmount(paymentDto.getAmount());
        paymentProcessEntity.setCardNumber(paymentDto.getCardNumber());
        paymentProcessEntity.setClientName(paymentDto.getClientName());
        paymentProcessEntity.setClientEmail(paymentDto.getClientEmail());
        paymentProcessEntity.setConfirmed(paymentDto.getConfirmed());
        return paymentProcessEntity;
    }

    public PaymentDto entityToDto(PaymentProcessEntity paymentEntity) {
        if (paymentEntity == null) {
            throw new RuntimeException("Payment process entity is null");
        }
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(paymentEntity.getId());
        paymentDto.setReferenceNumber(paymentEntity.getReferenceNumber());
        paymentDto.setItems(paymentEntity.getItems());
        paymentDto.setAmount(paymentEntity.getAmount());
        paymentDto.setCardNumber(paymentEntity.getCardNumber());
        paymentDto.setClientName(paymentEntity.getClientName());
        paymentDto.setClientEmail(paymentEntity.getClientEmail());
        paymentDto.setConfirmed(paymentEntity.getConfirmed());
        return paymentDto;
    }

}

