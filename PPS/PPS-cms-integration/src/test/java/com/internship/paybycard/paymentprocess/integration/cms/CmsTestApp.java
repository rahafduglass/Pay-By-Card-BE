package com.internship.paybycard.paymentprocess.integration.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.internship.paybycard")
public class CmsTestApp {
    public static void main(String[] args) {
        SpringApplication.run(CmsTestApp.class, args);
    }
}
