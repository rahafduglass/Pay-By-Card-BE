package com.internship.paybycard.paymentprocess.persistence;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentProcessJpaRepository extends JpaRepository<PaymentProcessEntity, Long> {
    @Query("SELECT p FROM PaymentProcessEntity p WHERE p.referenceNumber=:referenceNumber")
    PaymentProcessEntity findByReferenceNumber(@Param("referenceNumber") String referenceNumber);

    @Modifying
    @Transactional
    @Query("UPDATE PaymentProcessEntity p SET p.confirmed=:confirmed WHERE p.referenceNumber=:referenceNumber")
    Integer updatePaymentByReferenceNumber(@Param("referenceNumber") String referenceNumber, @Param("confirmed") Boolean confirmed);
}
