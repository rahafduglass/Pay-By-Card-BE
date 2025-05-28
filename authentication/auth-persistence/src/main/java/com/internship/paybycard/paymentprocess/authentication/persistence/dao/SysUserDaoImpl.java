package com.internship.paybycard.paymentprocess.authentication.persistence.dao;


import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.RegistrationDetails;
import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.SysUserDetails;
import com.internship.paybycard.paymentprocess.authentication.core.persistence.SysUserDao;
import com.internship.paybycard.paymentprocess.authentication.persistence.jpa.SysUserEntity;
import com.internship.paybycard.paymentprocess.authentication.persistence.jpa.SysUserJpaRepository;
import com.internship.paybycard.paymentprocess.authentication.persistence.mapper.SysUserDetailsMapper;
import com.internship.paybycard.paymentprocess.authentication.persistence.mapper.SysUserEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SysUserDaoImpl implements SysUserDao {

    private final SysUserJpaRepository sysUserJpaRepository;
    private final SysUserEntityMapper sysUserEntityMapper;
    private final SysUserDetailsMapper sysUserDetailsMapper;

    @Override
    public void save(RegistrationDetails registrationDetails) {
        log.info("Saving user to DB{}", registrationDetails.getUsername());
        sysUserJpaRepository.save(sysUserEntityMapper.toEntity(registrationDetails));
    }

    @Override
    public SysUserDetails findByUsername(String username) {
        log.info("Finding user by username {}", username);
        SysUserEntity user= sysUserJpaRepository.findByUsername(username);
        return sysUserDetailsMapper.toSysUserDetails(user);
    }

}
