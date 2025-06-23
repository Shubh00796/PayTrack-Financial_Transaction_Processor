package com.FinancialTransactionProcessor.events;

public interface EventPublisher {
    void publish(TransactionInitiatedEvent event);
}