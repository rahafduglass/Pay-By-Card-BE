package com.internship.paybycard.cardmanagement.persistence.jpa;


import com.internship.paybycard.cardmanagement.persistence.entity.CardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CardJpaRepository extends JpaRepository<CardEntity, Long> {


    @Query("SELECT c FROM CardEntity c WHERE c.cardNumber=:cardNumber AND c.CVV=:cvv AND  c.expiryDate=:expiryDate")
    Optional<CardEntity> findByCardNumberAndCvvAndExpiryDate(@Param("cardNumber") String cardNumber,@Param("cvv") String cvv,@Param("expiryDate") LocalDate expiryDate);

    @Transactional
    @Modifying
    @Query("DELETE CardEntity c WHERE c.cardNumber=:cardNumber")
    Integer deleteByCardNumber(@Param("cardNumber")String cardNumber);

    @Transactional
    @Modifying
    @Query("UPDATE CardEntity c SET c.balance=:balance , c.clientEmail=:clientEmail,c.clientName=:clientName WHERE c.CVV=:cvv AND c.cardNumber=:cardNumber AND c.expiryDate=:expiryDate")
    Integer updateCardBalanceAndClientEmailAndClientNameByCvvAndCardNumberAndExpiryDate(@Param("balance")BigDecimal balance, @Param("clientEmail")String clientEmail, @Param("clientName")String clientName, @Param("cvv") String cvv, @Param("cardNumber")String cardNumber,@Param("expiryDate")LocalDate expiryDate);


    @Transactional
    @Modifying
    @Query("UPDATE CardEntity c SET c.balance=:balance WHERE c.CVV=:cvv AND c.cardNumber=:cardNumber AND c.expiryDate=:expiryDate")
    int updateCardBalance(@Param("cardNumber")String cardNumber, @Param("cvv") String cvv,@Param("expiryDate") LocalDate expiryDate, @Param("balance") BigDecimal newBalance);
}
