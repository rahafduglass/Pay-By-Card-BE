package com.internship.paybycard.paymentprocess.infrastructure.persistence;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentProcessJpaRepository extends JpaRepository<PaymentProcessEntity, Long> {
}
