package com.internship.paybycard.paymentprocess.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.internship.paybycard")
public class EmailServiceTestApp {
    public static void main(String[] args) {
        SpringApplication.run(EmailServiceTestApp.class, args);
    }
}
