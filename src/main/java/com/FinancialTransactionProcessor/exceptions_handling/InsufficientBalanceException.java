package com.FinancialTransactionProcessor.exceptions_handling;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}


