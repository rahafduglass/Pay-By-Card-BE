package com.internship.paybycard.paymentprocess.infrastructure.persistence;


import com.internship.paybycard.paymentprocess.core.domain.dto.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.exception.PaymentNotFoundException;
import com.internship.paybycard.paymentprocess.core.infrastructure.PaymentDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentDaoImpl implements PaymentDao {

    private final PaymentEntityMapper paymentEntityMapper;

    private final PaymentProcessJpaRepository paymentProcessJpaRepository;

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto) {
        return paymentEntityMapper.entityToDto(paymentProcessJpaRepository.save(paymentEntityMapper.dtoToEntity(paymentDto)));
    }

    @Override
    public PaymentDto findPaymentByReferenceNumber(String referenceNumber) {
        return paymentEntityMapper.entityToDto(paymentProcessJpaRepository.findByReferenceNumber(referenceNumber));
    }

    @Override
    public void updatePaymentConfirmedByReferenceNumber(String referenceNumber, Boolean confirmed) {

    }
}
