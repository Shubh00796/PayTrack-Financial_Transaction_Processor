package com.FinancialTransactionProcessor.reposiotry_interfaces;


import com.FinancialTransactionProcessor.entities.TransactionAudit;
import com.FinancialTransactionProcessor.enums.TransactionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionAuditRepository extends JpaRepository<TransactionAudit, Long> {
    List<TransactionAudit> findByNewStatus(TransactionStatus newStatus);

    List<TransactionAudit> findByPreviousStatus(TransactionStatus previousStatus);

    Page<TransactionAudit> findAll(Pageable pageable);

    @Query("SELECT t FROM TransactionAudit t WHERE t.transactionId = :transactionId")
    Optional<TransactionAudit> findByTransactionId(@Param("transactionId") String transactionId);


    Page<TransactionAudit> findByNewStatus(TransactionStatus newStatus, Pageable pageable);

    Page<TransactionAudit> findAllByTransactionId(String transactionId, Pageable pageable);

}