package com.internship.paybycard.cardmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@EntityScan(basePackages = "com.internship.paybycard")
@SpringBootApplication
public class CardManagementSystem {
    public static void main(String[] args) {
        SpringApplication.run(CardManagementSystem.class, args);
    }
}
