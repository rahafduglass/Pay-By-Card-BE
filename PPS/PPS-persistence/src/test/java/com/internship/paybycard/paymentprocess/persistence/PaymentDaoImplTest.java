package com.internship.paybycard.paymentprocess.persistence;

import com.internship.paybycard.paymentprocess.core.domain.dto.payment.NullPaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.PaymentDto;
import com.internship.paybycard.paymentprocess.core.domain.dto.payment.RealPaymentDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PersistenceTestApp.class)
@ActiveProfiles("test")
@Transactional
public class PaymentDaoImplTest {

    @Autowired
    private PaymentDaoImpl paymentDaoImpl;

    @Autowired
    private PaymentJpaRepository paymentJpaRepository;

    @Autowired
    private PaymentEntityMapper paymentEntityMapper;

    private final  RealPaymentDto paymentDto=new RealPaymentDto();

    @BeforeEach
    public void setUp(){
   //     paymentDto = new RealPaymentDto();
        paymentDto.setItems("test items");
        paymentDto.setReferenceNumber("1234567890");
        paymentDto.setAmount(new BigDecimal("10.00"));
        paymentDto.setCardNumber("123456789");
        paymentDto.setClientName("test client");
        paymentDto.setClientEmail("test@email.com");
        paymentDto.setConfirmed(false);
    }

    @Test
    public void givenValidRealPaymentDto_whenCreatePayment_thenShouldReturnSavedPaymentDto() {
        PaymentDto savedPayment = paymentDaoImpl.createPayment(paymentDto);
        PaymentDto retrievedPayment = paymentEntityMapper.entityToDto(paymentJpaRepository.findById(savedPayment.getId()).orElse(null));
        assertEquals(savedPayment, retrievedPayment);
    }

    @Test
    public void givenValidReferenceNumber_whenCallFindPaymentByReferenceNumber_thenShouldReturnUpdatedPaymentDto() {
        PaymentDto savedPayment= paymentDaoImpl.createPayment(paymentDto);

        PaymentDto retrievedPayment = paymentDaoImpl.findPaymentByReferenceNumber(savedPayment.getReferenceNumber());
        assertFalse(retrievedPayment.isNull());
        assertEquals( savedPayment,retrievedPayment);
    }

    @Test
    public void givenValidReferenceNumber_whenCallUpdatePaymentConfirmed_thenShouldReturnOne(){
        PaymentDto savedPayment= paymentDaoImpl.createPayment(paymentDto);

        int result= paymentDaoImpl.updatePaymentConfirmed(savedPayment.getReferenceNumber(), true);
        assertEquals(1, result);
    }


    @Test
    public void givenInvalidReferenceNumber_whenCallUpdatePaymentConfirmed_thenShouldReturnZero(){
        int result= paymentDaoImpl.updatePaymentConfirmed(paymentDto.getReferenceNumber(), true);
        assertEquals(0, result);
    }
    @Test
    public void givenInValidReferenceNumber_whenCallFindPaymentByReferenceNumber_thenShouldReturnNullObject() {
        PaymentDto retrievedPayment = paymentDaoImpl.findPaymentByReferenceNumber(paymentDto.getReferenceNumber());
        assertTrue(retrievedPayment.isNull());
    }

}
