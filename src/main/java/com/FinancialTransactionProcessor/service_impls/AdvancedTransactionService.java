package com.FinancialTransactionProcessor.service_impls;

import com.FinancialTransactionProcessor.entities.Transaction;
import com.FinancialTransactionProcessor.enums.TransactionStatus;
import com.FinancialTransactionProcessor.enums.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvancedTransactionService {

    private final List<Transaction> transactions;


    // 1. Find by transaction ID
    public Optional<Transaction> findByTransactionId(String transactionId) {
        return transactions.stream()
                .filter(transaction -> transactionId.equals(transaction.getTransactionId()))
                .findFirst();
    }

    //2. Find By Status
    public List<Transaction> findAllbyTrasnactionsStatus(TransactionStatus status) {
        return transactions.stream()
                .filter(transaction -> transaction.equals(status))
                .collect(Collectors.toList());
    }

    public List<Transaction> findAllByType(TransactionType type) {
        return transactions.stream()
                .filter(transaction -> transaction.equals(type))
                .collect(Collectors.toList());
    }

    public List<Transaction> findByAccount(String accountId) {
        return transactions.stream()
                .filter(transaction -> accountId.equals(transaction.getToAccountId()))
                .collect(Collectors.toList());
    }

}
