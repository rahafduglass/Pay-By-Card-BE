package com.internship.paybycard.paymentprocess.authentication.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserJpaRepository extends JpaRepository<SysUserEntity,Long> {
    SysUserEntity findByUsername(String username);
}
