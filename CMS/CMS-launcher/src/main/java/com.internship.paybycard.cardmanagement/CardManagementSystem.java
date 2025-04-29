package com.internship.paybycard.cardmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {"com.internship.paybycard.*"})
@EntityScan(basePackages = "com.internship.paybycard.*")
@EnableJpaRepositories(basePackages = "com.internship.paybycard.*")
public class CardManagementSystem {
    public static void main(String[] args) {
        SpringApplication.run(CardManagementSystem.class, args);
    }
}
