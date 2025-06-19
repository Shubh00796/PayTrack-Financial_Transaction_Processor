package com.FinancialTransactionProcessor.exceptions_handling;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}