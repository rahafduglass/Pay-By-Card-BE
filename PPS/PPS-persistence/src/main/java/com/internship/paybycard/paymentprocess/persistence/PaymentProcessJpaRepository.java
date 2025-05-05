package com.internship.paybycard.paymentprocess.persistence;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentProcessJpaRepository extends JpaRepository<PaymentProcessEntity, Long> {
    @Query("SELECT p FROM PaymentProcessEntity p WHERE p.referenceNumber=:referenceNumber")
    PaymentProcessEntity findByReferenceNumber(@Param("referenceNumber") String referenceNumber);
}
