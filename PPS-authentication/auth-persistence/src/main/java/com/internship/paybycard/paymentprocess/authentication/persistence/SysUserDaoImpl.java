package com.internship.paybycard.paymentprocess.authentication.persistence;


import com.internship.paybycard.paymentprocess.authentication.core.persistence.SysUserDetails;
import com.internship.paybycard.paymentprocess.authentication.core.persistence.SysUserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SysUserDaoImpl implements SysUserDao {

    private final SysUserJpaRepository sysUserJpaRepository;
    private final SysUserEntityMapper sysUserEntityMapper;

    @Override
    public void save(SysUserDetails sysUserDetails) {
        sysUserJpaRepository.save(sysUserEntityMapper.toEntity(sysUserDetails));
    }
}
