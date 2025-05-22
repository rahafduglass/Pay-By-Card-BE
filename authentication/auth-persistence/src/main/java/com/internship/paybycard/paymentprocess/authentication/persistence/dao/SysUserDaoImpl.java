package com.internship.paybycard.paymentprocess.authentication.persistence.dao;


import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.RegistrationDetails;
import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.SysUserDetails;
import com.internship.paybycard.paymentprocess.authentication.core.persistence.SysUserDao;
import com.internship.paybycard.paymentprocess.authentication.persistence.jpa.SysUserEntity;
import com.internship.paybycard.paymentprocess.authentication.persistence.jpa.SysUserJpaRepository;
import com.internship.paybycard.paymentprocess.authentication.persistence.mapper.SysUserDetailsMapper;
import com.internship.paybycard.paymentprocess.authentication.persistence.mapper.SysUserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SysUserDaoImpl implements SysUserDao {

    private final SysUserJpaRepository sysUserJpaRepository;
    private final SysUserEntityMapper sysUserEntityMapper;
    private final SysUserDetailsMapper sysUserDetailsMapper;

    @Override
    public void save(RegistrationDetails registrationDetails) {
        sysUserJpaRepository.save(sysUserEntityMapper.toEntity(registrationDetails));
    }

    @Override
    public SysUserDetails findByUsername(String username) {
        SysUserEntity user= sysUserJpaRepository.findByUsername(username);
        return sysUserDetailsMapper.toSysUserDetails(user);
    }

}
