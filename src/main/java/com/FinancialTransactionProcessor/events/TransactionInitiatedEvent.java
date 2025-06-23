package com.FinancialTransactionProcessor.events;

import com.FinancialTransactionProcessor.dtos.CreateTransactionDTO;
import org.springframework.context.ApplicationEvent;

public class TransactionInitiatedEvent extends ApplicationEvent {
    private final CreateTransactionDTO createTransactionDTO;

    public TransactionInitiatedEvent(Object source, CreateTransactionDTO dto) {
        super(source);
        this.createTransactionDTO = dto;
    }

    public CreateTransactionDTO getCreateTransactionDTO() {
        return createTransactionDTO;
    }
}
