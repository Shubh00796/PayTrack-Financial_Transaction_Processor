package com.FinancialTransactionProcessor.service_interfaces;

import com.FinancialTransactionProcessor.dtos.CreateTransactionDTO;
import com.FinancialTransactionProcessor.dtos.TransactionResponseDTO;
import com.FinancialTransactionProcessor.enums.TransactionStatus;
import com.FinancialTransactionProcessor.enums.TransactionType;

import java.math.BigDecimal;

public interface TransactionService {

    TransactionResponseDTO initiateTransaction(CreateTransactionDTO createTransactionDTO);

    void cancelTransaction(String transactionId);

    void reverseTransaction(String transactionId);

    void processTransaction(String transactionId);

    void updateTransactionStatus(String transactionId, TransactionStatus status);

    void updateTransactionType(String transactionId, TransactionType type);

    void refundTransaction(String transactionId);

    void chargebackTransaction(String transactionId);

    void transferFunds(String fromAccountId, String toAccountId, BigDecimal amount);

    void depositFunds(String accountId, BigDecimal amount);

    void withdrawFunds(String accountId, BigDecimal amount);
}