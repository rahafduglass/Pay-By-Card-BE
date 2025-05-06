package com.internship.paybycard.cardmanagement.persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.internship.paybycard")
public class PersistenceTestApp {
    public static void main(String[] args) {
        SpringApplication.run(PersistenceTestApp.class, args);
    }
}
