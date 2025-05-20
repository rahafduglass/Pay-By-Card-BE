package com.internship.paybycard.paymentprocess;

import com.internship.paybycard.paymentprocess.authentication.domain.service.security.SysUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfig {


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(SysUserDetailsService sysUserDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        //set the UserDetailsService to be used by the DaoAuthenticationProvider
        authenticationProvider.setUserDetailsService(sysUserDetailsService.userDetailsService());

        //set the password encoder to be used by the authentication provider
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        //returning the provider
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
