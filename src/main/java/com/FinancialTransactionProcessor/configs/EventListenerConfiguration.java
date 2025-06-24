package com.FinancialTransactionProcessor.configs;

import com.FinancialTransactionProcessor.events.TransactionInitiatedEventHandler;
import com.FinancialTransactionProcessor.events.TransactionInitiatedEventHandlerImpl;
import com.FinancialTransactionProcessor.service_interfaces.TransactionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventListenerConfiguration {
    @Bean
    public TransactionInitiatedEventHandler transactionInitiatedEventHandler(TransactionService transactionService) {
        return new TransactionInitiatedEventHandlerImpl(transactionService);
    }
}