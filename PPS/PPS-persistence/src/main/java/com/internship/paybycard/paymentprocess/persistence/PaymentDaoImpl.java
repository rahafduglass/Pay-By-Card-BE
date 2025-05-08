package com.internship.paybycard.paymentprocess.persistence;


import com.internship.paybycard.paymentprocess.core.domain.dto.payment.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.RealPaymentDto;
import com.internship.paybycard.paymentprocess.core.persistence.PaymentDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PaymentDaoImpl implements PaymentDao {

    private final PaymentEntityMapper paymentEntityMapper;
    private final PaymentProcessJpaRepository paymentProcessJpaRepository;

    @Override
    public PaymentDto createPayment(RealPaymentDto realPaymentDto) {
        log.debug("creating payment {}", realPaymentDto);
        return paymentEntityMapper.entityToDto(paymentProcessJpaRepository.save(paymentEntityMapper.dtoToEntity(realPaymentDto)));
    }

    @Override
    public PaymentDto findPaymentByReferenceNumber(String referenceNumber) {
        log.debug("finding payment by reference number {}", referenceNumber);
        return paymentEntityMapper.entityToDto(paymentProcessJpaRepository.findByReferenceNumber(referenceNumber));
    }

    @Override
    public int updatePaymentConfirmedByReferenceNumber(String referenceNumber, Boolean confirmed) {
        log.debug("updating payment confirmed by reference number {}", referenceNumber);
        return paymentProcessJpaRepository.updatePaymentByReferenceNumber(referenceNumber, confirmed);
    }
}
