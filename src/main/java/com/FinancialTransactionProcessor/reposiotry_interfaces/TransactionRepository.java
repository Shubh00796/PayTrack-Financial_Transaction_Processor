package com.FinancialTransactionProcessor.reposiotry_interfaces;


import com.FinancialTransactionProcessor.entities.Transaction;
import com.FinancialTransactionProcessor.enums.TransactionStatus;
import com.FinancialTransactionProcessor.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByTransactionId(String transactionId);
    Page<Transaction> findByStatus(TransactionStatus status, Pageable pageable);
    Page<Transaction> findByFromAccountIdOrToAccountId(String fromAccountId, String toAccountId, Pageable pageable);
    @Query("SELECT t FROM Transaction t WHERE (t.fromAccountId = :accountId OR t.toAccountId = :accountId) AND t.status = :status")
    Page<Transaction> findByAccountIdAndStatus(
            @Param("accountId") String accountId,
            @Param("status") TransactionStatus status,
            Pageable pageable
    );
    @Query("SELECT t FROM Transaction t WHERE t.transactionType = :type AND t.createdAt BETWEEN :startDate AND :endDate")
    List<Transaction> findByTypeAndDateRange(
            @Param("type") TransactionType type,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.fromAccountId = :accountId AND t.status = 'COMPLETED' AND t.createdAt >= :startDate")
    BigDecimal getTotalDebitAmount(@Param("accountId") String accountId, @Param("startDate") LocalDateTime startDate);
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.fromAccountId = :accountId AND t.createdAt >= :startDate")
    Long getTransactionCountByAccount(@Param("accountId") String accountId, @Param("startDate") LocalDateTime startDate);
    @Query("SELECT t FROM Transaction t WHERE t.paymentMethod = :paymentMethod")
    List<Transaction> findByPaymentMethod(@Param("paymentMethod") String paymentMethod);
}