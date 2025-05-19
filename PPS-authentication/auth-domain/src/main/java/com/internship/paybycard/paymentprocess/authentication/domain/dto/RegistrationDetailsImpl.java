package com.internship.paybycard.paymentprocess.authentication.domain.dto;

import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.RegistrationDetails;
import lombok.Data;

@Data
public class RegistrationDetailsImpl implements RegistrationDetails {
    private String username;
    private String password;
    private String email;
}
