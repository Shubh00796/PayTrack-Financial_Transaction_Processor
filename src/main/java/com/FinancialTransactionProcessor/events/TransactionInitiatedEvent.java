package com.FinancialTransactionProcessor.events;

import com.FinancialTransactionProcessor.dtos.CreateTransactionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionInitiatedEvent {
    private static final long serialVersionUID = 1L;

    private String transactionId;
    private CreateTransactionDTO createTransactionDTO;

}