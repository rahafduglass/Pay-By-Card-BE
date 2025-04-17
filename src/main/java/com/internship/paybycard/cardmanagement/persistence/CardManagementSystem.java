package com.internship.paybycard.cardmanagement.persistence;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.internship.paybycard")
public class CardManagementSystem {
    public static void main(String[] args) {
        SpringApplication.run(CardManagementSystem.class, args);
    }
}
