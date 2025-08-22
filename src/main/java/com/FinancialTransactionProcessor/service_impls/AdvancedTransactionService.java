package com.FinancialTransactionProcessor.service_impls;

import com.FinancialTransactionProcessor.entities.Transaction;
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

}
