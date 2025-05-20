package com.internship.paybycard.paymentprocess.authentication.domain.service.security;

import com.internship.paybycard.paymentprocess.authentication.core.persistence.SysUserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SysUserDetailsService {
    private final SysUserDao sysUserDao;

    public UserDetailsService userDetailsService() {
        return sysUserDao::findByUsername;
    }
}
