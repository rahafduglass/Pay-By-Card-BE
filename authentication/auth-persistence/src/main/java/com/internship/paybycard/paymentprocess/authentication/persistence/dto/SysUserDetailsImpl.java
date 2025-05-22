package com.internship.paybycard.paymentprocess.authentication.persistence.dto;

import com.internship.paybycard.paymentprocess.authentication.core.domain.dto.SysUserDetails;
import lombok.Data;

@Data
public class SysUserDetailsImpl implements SysUserDetails {
    private Long userId;
    private String username;
    private String password;
    private String email;
}