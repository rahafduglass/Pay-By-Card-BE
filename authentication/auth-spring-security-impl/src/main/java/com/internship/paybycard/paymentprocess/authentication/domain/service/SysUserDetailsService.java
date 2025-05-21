package com.internship.paybycard.paymentprocess.authentication.domain.service;

import com.internship.paybycard.paymentprocess.authentication.core.persistence.SysUserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SysUserDetailsService {
    private final SysUserDao sysUserDao;

    public UserDetailsService userDetailsService() {
        return e -> (UserDetails) sysUserDao.findByUsername(e);
    }
}
