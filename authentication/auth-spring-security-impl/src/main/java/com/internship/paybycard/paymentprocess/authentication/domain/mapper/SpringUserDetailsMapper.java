package com.internship.paybycard.paymentprocess.authentication.domain.mapper;

import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.SysUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class SpringUserDetailsMapper {

    public UserDetails toUserDetails(SysUserDetails sysUserDetails) {
        return new UserDetails(){

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }
            @Override
            public String getPassword() {
                return sysUserDetails.getPassword();
            }
            @Override
            public String getUsername() {
                return sysUserDetails.getUsername();
            }
            @Override
            public boolean isAccountNonExpired() {
                return true;
            }
            @Override
            public boolean isAccountNonLocked() {
                return true;
            }
            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }
            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }

}
