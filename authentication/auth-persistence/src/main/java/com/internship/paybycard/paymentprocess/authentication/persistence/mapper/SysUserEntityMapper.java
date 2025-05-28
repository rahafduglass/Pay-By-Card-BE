package com.internship.paybycard.paymentprocess.authentication.persistence.mapper;


import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.RegistrationDetails;
import com.internship.paybycard.paymentprocess.authentication.persistence.jpa.SysUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SysUserEntityMapper {
    public SysUserEntity toEntity(RegistrationDetails sysUserDto) {
        log.debug("mapping RegistratoinDetails to SysUserEntity, username= {}", sysUserDto.getUsername());
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setPassword(sysUserDto.getPassword());
        sysUserEntity.setUsername(sysUserDto.getUsername());
        sysUserEntity.setEmail(sysUserDto.getEmail());
        return sysUserEntity;
    }
}
