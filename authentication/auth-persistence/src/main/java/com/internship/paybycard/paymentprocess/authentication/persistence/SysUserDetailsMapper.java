package com.internship.paybycard.paymentprocess.authentication.persistence;

import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.SysUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SysUserDetailsMapper {
    public SysUserDetails toSysUserDetails(UserDetails userDetails) {
        SysUserDetailsImpl sysUserDetailsImpl = new SysUserDetailsImpl();
        sysUserDetailsImpl.setUsername(userDetails.getUsername());
        sysUserDetailsImpl.setPassword(userDetails.getPassword());
        return sysUserDetailsImpl;
    }
}
