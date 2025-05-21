package com.internship.paybycard.paymentprocess.authentication.core.persistence;


import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.RegistrationDetails;
import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.SysUserDetails;


public interface SysUserDao {
    void save(RegistrationDetails registrationDetails);
    SysUserDetails findByUsername(String username);
}