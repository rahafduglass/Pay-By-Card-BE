package com.internship.paybycard.cardmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan(basePackages = "com.internship.paybycard.cardmanagement.entity")
public class CardManagementSystem {
    public static void main(String[] args) {
        SpringApplication.run(CardManagementSystem.class, args);
    }
}
