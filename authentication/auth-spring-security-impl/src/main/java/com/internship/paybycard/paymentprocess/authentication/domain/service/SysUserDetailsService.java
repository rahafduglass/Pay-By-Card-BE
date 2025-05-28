package com.internship.paybycard.paymentprocess.authentication.domain.service;

import com.internship.paybycard.paymentprocess.authentication.core.persistence.SysUserDao;
import com.internship.paybycard.paymentprocess.authentication.domain.mapper.SpringUserDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SysUserDetailsService {
    private final SysUserDao sysUserDao;
    private final SpringUserDetailsMapper springUserDetailsMapper;

    public UserDetailsService userDetailsService() {
        return e ->
                springUserDetailsMapper.toUserDetails(sysUserDao.findByUsername(e));
    }
}
