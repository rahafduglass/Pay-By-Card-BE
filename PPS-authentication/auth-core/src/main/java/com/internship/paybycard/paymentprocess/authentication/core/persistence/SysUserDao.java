package com.internship.paybycard.paymentprocess.authentication.core.persistence;


import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.RegistrationDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserDao {
    void save(RegistrationDetails registrationDetails);

    //TODO remove spring from core :D create ur custom user details
    UserDetails findByUsername(String username);
}