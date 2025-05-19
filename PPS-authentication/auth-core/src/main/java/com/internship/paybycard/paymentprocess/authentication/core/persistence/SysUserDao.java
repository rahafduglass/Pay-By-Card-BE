package com.internship.paybycard.paymentprocess.authentication.core.persistence;


import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.RegistrationDetails;

public interface SysUserDao {
    void save(RegistrationDetails registrationDetails);
}
