package com.internship.paybycard.paymentprocess.authentication.persistence.mapper;

import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.SysUserDetails;
import com.internship.paybycard.paymentprocess.authentication.persistence.dto.SysUserDetailsImpl;
import com.internship.paybycard.paymentprocess.authentication.persistence.jpa.SysUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SysUserDetailsMapper {
    public SysUserDetails toSysUserDetails(SysUserEntity userEntity) {
        log.debug("mapping SysUserEntity to SysUserDetails, username= {}", userEntity.getUsername());
        SysUserDetailsImpl sysUserDetailsImpl = new SysUserDetailsImpl();
        sysUserDetailsImpl.setUsername(userEntity.getUsername());
        sysUserDetailsImpl.setPassword(userEntity.getPassword());
        return sysUserDetailsImpl;
    }
}