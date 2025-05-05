package com.internship.paybycard.paymentprocess.persistence;


import com.internship.paybycard.paymentprocess.core.domain.dto.PaymentDto;
import com.internship.paybycard.paymentprocess.core.persistence.PaymentDao;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentDaoImpl implements PaymentDao {

    private final PaymentEntityMapper paymentEntityMapper;

    private final PaymentProcessJpaRepository paymentProcessJpaRepository;

    private final Logger log = LoggerFactory.getLogger(PaymentDaoImpl.class);

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto) {
        log.debug("creating payment {}", paymentDto);
        return paymentEntityMapper.entityToDto(paymentProcessJpaRepository.save(paymentEntityMapper.dtoToEntity(paymentDto)));
    }

    @Override
    public PaymentDto findPaymentByReferenceNumber(String referenceNumber) {
        log.debug("finding payment by reference number {}", referenceNumber);
        return paymentEntityMapper.entityToDto(paymentProcessJpaRepository.findByReferenceNumber(referenceNumber));
    }

    @Override
    public void updatePaymentConfirmedByReferenceNumber(String referenceNumber, Boolean confirmed) {
        log.debug("updating payment confirmed by reference number {}", referenceNumber);
    }
}
