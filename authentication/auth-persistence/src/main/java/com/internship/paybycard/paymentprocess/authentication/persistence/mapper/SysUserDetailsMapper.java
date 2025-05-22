package com.internship.paybycard.paymentprocess.authentication.persistence.mapper;

import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.SysUserDetails;
import com.internship.paybycard.paymentprocess.authentication.persistence.dto.SysUserDetailsImpl;
import com.internship.paybycard.paymentprocess.authentication.persistence.jpa.SysUserEntity;
import org.springframework.stereotype.Component;

@Component
public class SysUserDetailsMapper {
    public SysUserDetails toSysUserDetails(SysUserEntity userEntity) {
        SysUserDetailsImpl sysUserDetailsImpl = new SysUserDetailsImpl();
        sysUserDetailsImpl.setUsername(userEntity.getUsername());
        sysUserDetailsImpl.setPassword(userEntity.getPassword());
        return sysUserDetailsImpl;
    }
}