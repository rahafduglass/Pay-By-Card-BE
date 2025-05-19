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
    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public PaymentDto createPayment(RealPaymentDto realPaymentDto) {
        log.debug("creating payment {}", realPaymentDto);
        return paymentEntityMapper.entityToDto(paymentJpaRepository.save(paymentEntityMapper.dtoToEntity(realPaymentDto)));
    }

    @Override
    public PaymentDto findPaymentByReferenceNumber(String referenceNumber) {
        log.debug("finding payment by reference number {}", referenceNumber);
        return paymentEntityMapper.entityToDto(paymentJpaRepository.findByReferenceNumber(referenceNumber));
    }

    @Override
    public int updatePaymentConfirmed(String referenceNumber, Boolean confirmed) {
        log.debug("updating payment confirmed by reference number {}", referenceNumber);
        return paymentJpaRepository.updatePaymentByReferenceNumber(referenceNumber, confirmed);
    }
}
