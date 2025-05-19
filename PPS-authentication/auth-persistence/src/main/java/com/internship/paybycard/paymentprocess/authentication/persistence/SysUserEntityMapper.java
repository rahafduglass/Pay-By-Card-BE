package com.internship.paybycard.paymentprocess.authentication.persistence;


import com.internship.paybycard.paymentprocess.authentication.core.persistence.SysUserDetails;
import org.springframework.stereotype.Component;

@Component
public class SysUserEntityMapper {
    public SysUserEntity toEntity(SysUserDetails sysUserDto) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUserId(sysUserDto.getUserId());
        sysUserEntity.setPassword(sysUserDto.getPassword());
        sysUserEntity.setEmail(sysUserDto.getEmail());
        return sysUserEntity;
    }
}
