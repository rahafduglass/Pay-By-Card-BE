package com.internship.paybycard.cardmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {  "com.internship.paybycard",  "com.internship.paybycard.configuration",   "com.internship.paybycard.cardmanagement.persistence" })
@EntityScan(basePackages = "com.internship.paybycard.cardmanagement.persistence")
@EnableJpaRepositories(basePackages = "com.internship.paybycard.cardmanagement.persistence")
public class CardManagementSystem {
    public static void main(String[] args) {
        SpringApplication.run(CardManagementSystem.class, args);
    }
}
