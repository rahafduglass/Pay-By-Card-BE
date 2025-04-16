package com.internship.paybycard.cardmanagement.persistence.jpa;

import com.internship.paybycard.cardmanagement.persistence.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardJpaRepository extends JpaRepository<CardEntity, String> {
}
