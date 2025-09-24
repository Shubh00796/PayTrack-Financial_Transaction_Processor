package com.FinancialTransactionProcessor.service_impls;

import com.FinancialTransactionProcessor.entities.Transaction;
import com.FinancialTransactionProcessor.enums.TransactionStatus;
import com.FinancialTransactionProcessor.enums.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvancedTransactionService {

    private final List<Transaction> transactions;

    // 🔍 Find by transaction ID
    public Optional<Transaction> findByTransactionId(String transactionId) {
        return transactions.stream()
                .filter(tx -> transactionId.equals(tx.getTransactionId()))
                .findFirst();
    }

    // 🔍 Find by status
    public List<Transaction> findAllByStatus(TransactionStatus status) {
        return transactions.stream()
                .filter(tx -> tx.getStatus() == status)
                .collect(Collectors.toList());
    }

    // 🔍 Find by type
    public List<Transaction> findAllByType(TransactionType type) {
        return transactions.stream()
                .filter(tx -> tx.getTransactionType() == type)
                .collect(Collectors.toList());
    }

    // 🔍 Find by account
    public List<Transaction> findByAccount(String accountId) {
        return transactions.stream()
                .filter(tx -> accountId.equals(tx.getToAccountId()))
                .collect(Collectors.toList());
    }

    // 🔍 Find by status and account
    public List<Transaction> findByStatusAndAccount(String accountId, TransactionStatus status) {
        return transactions.stream()
                .filter(tx -> accountId.equals(tx.getToAccountId()) && tx.getStatus() == status)
                .collect(Collectors.toList());
    }

    // 🔍 Find by type and exact date
    public List<Transaction> findByTypeAndDate(TransactionType type, LocalDateTime date) {
        return transactions.stream()
                .filter(tx -> tx.getTransactionType() == type && tx.getCreatedAt().equals(date))
                .collect(Collectors.toList());
    }

    // 📊 Group by type
    public Map<TransactionType, List<Transaction>> groupByType() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getTransactionType));
    }

    // 📊 Group by account and status
    public Map<String, Map<TransactionStatus, List<Transaction>>> groupByAccountAndStatus() {
        return transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getFromAccountId,
                        Collectors.groupingBy(Transaction::getStatus)
                ));
    }

    // 💰 Total amount by type
    public Map<TransactionType, BigDecimal> totalAmountByType() {
        return transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getTransactionType,
                        Collectors.mapping(Transaction::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ));
    }

    // ✅ Partition by success
    public Map<Boolean, List<Transaction>> partitionBySuccess() {
        return transactions.stream()
                .collect(Collectors.partitioningBy(
                        tx -> tx.getStatus() == TransactionStatus.COMPLETED
                ));
    }

    // 🔝 Top N transactions by amount
    public List<Transaction> getTopNTransactions(int n) {
        return transactions.stream()
                .sorted(Comparator.comparing(Transaction::getAmount).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    // ❌ Find all by failure reason
    public List<Transaction> getAllByFailureReason(String reason) {
        return transactions.stream()
                .filter(tx -> reason.equals(tx.getFailureReason()))
                .collect(Collectors.toList());
    }

    // 📊 Group by failure reason and transaction ID
    public Map<String, Map<String, List<Transaction>>> groupByFailureReasonAndTransactionId() {
        return transactions.stream()
                .filter(tx -> tx.getFailureReason() != null)
                .collect(Collectors.groupingBy(
                        Transaction::getFailureReason,
                        Collectors.groupingBy(Transaction::getTransactionId)
                ));
    }
}
