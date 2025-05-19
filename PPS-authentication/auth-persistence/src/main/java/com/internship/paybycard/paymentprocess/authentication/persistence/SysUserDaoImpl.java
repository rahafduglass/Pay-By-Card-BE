package com.internship.paybycard.paymentprocess.authentication.persistence;


import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.RegistrationDetails;
import com.internship.paybycard.paymentprocess.authentication.core.presentation.command.RegistrationCommand;
import com.internship.paybycard.paymentprocess.authentication.core.persistence.SysUserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SysUserDaoImpl implements SysUserDao {

    private final SysUserJpaRepository sysUserJpaRepository;
    private final SysUserEntityMapper sysUserEntityMapper;

    @Override
    public void save(RegistrationDetails registrationDetails) {
        sysUserJpaRepository.save(sysUserEntityMapper.toEntity(registrationDetails));
    }
}
