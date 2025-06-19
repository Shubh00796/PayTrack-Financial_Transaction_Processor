package com.FinancialTransactionProcessor.reposiotry_services;

import com.FinancialTransactionProcessor.entities.Transaction;
import com.FinancialTransactionProcessor.enums.PaymentMethod;
import com.FinancialTransactionProcessor.enums.TransactionStatus;
import com.FinancialTransactionProcessor.enums.TransactionType;
import com.FinancialTransactionProcessor.exceptions_handling.ResourceNotFoundException;
import com.FinancialTransactionProcessor.reposiotry_interfaces.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionRepoService {

    private final TransactionRepository transactionRepository;

    public Transaction findByTransactionId(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction with ID " + transactionId + " not found"));
    }

    public Page<Transaction> findAll(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    public Page<Transaction> findByStatus(TransactionStatus status, Pageable pageable) {
        return transactionRepository.findByStatus(status, pageable);
    }

    public Page<Transaction> findByAccount(String accountId, Pageable pageable) {
        return transactionRepository.findByFromAccountIdOrToAccountId(accountId, accountId, pageable);
    }

    public Page<Transaction> findByAccountAndStatus(String accountId, TransactionStatus status, Pageable pageable) {
        return transactionRepository.findByAccountIdAndStatus(accountId, status, pageable);
    }

    public List<Transaction> findByTypeAndDateRange(TransactionType type, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByTypeAndDateRange(type, startDate, endDate);
    }

    public BigDecimal getTotalDebitAmount(String accountId, LocalDateTime startDate) {
        return transactionRepository.getTotalDebitAmount(accountId, startDate);
    }

    public Long getTransactionCountByAccount(String accountId, LocalDateTime startDate) {
        return transactionRepository.getTransactionCountByAccount(accountId, startDate);
    }

    public List<Transaction> findByPaymentMethod(PaymentMethod paymentMethod) {
        return transactionRepository.findByPaymentMethod(paymentMethod.name());
    }

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void delete(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    public boolean existsByTransactionId(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId).isPresent();
    }
}
