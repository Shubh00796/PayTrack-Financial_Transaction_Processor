package com.FinancialTransactionProcessor.events;

public interface TransactionInitiatedEventHandler {
    void handle(TransactionInitiatedEvent event);
}
