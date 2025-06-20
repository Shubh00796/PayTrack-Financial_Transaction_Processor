package com.FinancialTransactionProcessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaAuditing
public class FinancialTransactionProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialTransactionProcessorApplication.class, args);
    }

}
