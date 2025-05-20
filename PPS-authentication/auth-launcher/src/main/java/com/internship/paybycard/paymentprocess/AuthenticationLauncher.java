package com.internship.paybycard.paymentprocess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.internship.paybycard")
public class AuthenticationLauncher {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationLauncher.class, args);
    }
}
