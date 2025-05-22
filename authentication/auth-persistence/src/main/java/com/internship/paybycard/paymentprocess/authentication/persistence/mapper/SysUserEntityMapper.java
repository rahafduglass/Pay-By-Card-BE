package com.internship.paybycard.paymentprocess.authentication.persistence.mapper;


import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.RegistrationDetails;
import com.internship.paybycard.paymentprocess.authentication.persistence.jpa.SysUserEntity;
import org.springframework.stereotype.Component;

@Component
public class SysUserEntityMapper {
    public SysUserEntity toEntity(RegistrationDetails sysUserDto) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setPassword(sysUserDto.getPassword());
        sysUserEntity.setUsername(sysUserDto.getUsername());
        sysUserEntity.setEmail(sysUserDto.getEmail());
        return sysUserEntity;
    }
}
