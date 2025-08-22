package com.FinancialTransactionProcessor.service_impls;

import com.FinancialTransactionProcessor.entities.Transaction;
import com.FinancialTransactionProcessor.enums.TransactionStatus;
import com.FinancialTransactionProcessor.enums.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

    public List<Transaction> findByStausAndAccount(String accountId, TransactionStatus status) {
        return transactions.stream()
                .filter(transaction -> accountId.equals(transaction.getToAccountId()) &&
                        transaction.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Transaction> findByTypeAndDateRange(TransactionType type, LocalDateTime createdAt) {
        return transactions.stream()
                .filter(transaction -> transaction.getTransactionType() == type &&
                        transaction.getCreatedAt() == createdAt
                )
                .collect(Collectors.toList());
    }

    public Map<TransactionType, List<Transaction>> groupByType(Transaction transaction) {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getTransactionType));
    }

    public Map<String, Map<TransactionStatus, List<Transaction>>> groupByAccountAndStatus() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getFromAccountId, Collectors.groupingBy(Transaction::getStatus)));
    }


    public Map<TransactionType, BigDecimal> totalAmountByType() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getTransactionType,
                        Collectors.mapping(Transaction::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }


    public Map<Boolean, List<Transaction>> partitionBySuccess() {
        return transactions.stream()
                .collect(Collectors.partitioningBy(
                        transaction -> transaction.getStatus() == TransactionStatus.COMPLETED));
    }

    public List<Transaction> getTopNTransactions(int n) {
        return transactions.stream()
                .sorted(Comparator.comparing(Transaction::getAmount).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }



}

