package com.internship.paybycard.paymentprocess.authentication.core.persistence;


public interface SysUserDetails {
    public Long getUserId();
    public String getUsername();
    public String getPassword();
    public String getEmail();
}
