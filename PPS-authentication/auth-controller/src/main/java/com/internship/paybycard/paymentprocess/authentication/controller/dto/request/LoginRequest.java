package com.internship.paybycard.paymentprocess.authentication.controller.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
