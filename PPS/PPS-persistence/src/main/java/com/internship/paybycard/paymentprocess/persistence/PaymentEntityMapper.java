package com.internship.paybycard.paymentprocess.persistence;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.NullPaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.RealPaymentDto;
import org.springframework.stereotype.Component;

@Component
public class PaymentEntityMapper {

    public PaymentProcessEntity dtoToEntity(PaymentDto realPaymentDto) {
        if(realPaymentDto == null) {
            throw new IllegalArgumentException("Payment DTO is null");
        }
        PaymentProcessEntity paymentProcessEntity = new PaymentProcessEntity();
        paymentProcessEntity.setId(realPaymentDto.getId());
        paymentProcessEntity.setReferenceNumber(realPaymentDto.getReferenceNumber());
        paymentProcessEntity.setItems(realPaymentDto.getItems());
        paymentProcessEntity.setAmount(realPaymentDto.getAmount());
        paymentProcessEntity.setCardNumber(realPaymentDto.getCardNumber());
        paymentProcessEntity.setClientName(realPaymentDto.getClientName());
        paymentProcessEntity.setClientEmail(realPaymentDto.getClientEmail());
        paymentProcessEntity.setConfirmed(realPaymentDto.getConfirmed());
        return paymentProcessEntity;
    }

    public PaymentDto entityToDto(PaymentProcessEntity paymentEntity) {
        if (paymentEntity == null) {
            return new NullPaymentDto();
        }
        RealPaymentDto realPaymentDto = new RealPaymentDto();
        realPaymentDto.setId(paymentEntity.getId());
        realPaymentDto.setReferenceNumber(paymentEntity.getReferenceNumber());
        realPaymentDto.setItems(paymentEntity.getItems());
        realPaymentDto.setAmount(paymentEntity.getAmount());
        realPaymentDto.setCardNumber(paymentEntity.getCardNumber());
        realPaymentDto.setClientName(paymentEntity.getClientName());
        realPaymentDto.setClientEmail(paymentEntity.getClientEmail());
        realPaymentDto.setConfirmed(paymentEntity.getConfirmed());
        return realPaymentDto;
    }

}

