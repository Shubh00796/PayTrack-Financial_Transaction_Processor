package com.FinancialTransactionProcessor.reposiotry_services;

import com.FinancialTransactionProcessor.entities.TransactionAudit;
import com.FinancialTransactionProcessor.enums.TransactionStatus;
import com.FinancialTransactionProcessor.exceptions_handling.ResourceNotFoundException;
import com.FinancialTransactionProcessor.reposiotry_interfaces.TransactionAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionAuditRepoService {

    private final TransactionAuditRepository transactionAuditRepository;

    public TransactionAudit findById(Long id) {
        return transactionAuditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction audit with ID " + id + " not found"));
    }

    public TransactionAudit findByTransactionId(String transactionId) {
        return transactionAuditRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction audit for transaction ID " + transactionId + " not found"));
    }

    public List<TransactionAudit> findByNewStatus(TransactionStatus newStatus) {
        return transactionAuditRepository.findByNewStatus(newStatus);
    }

    public List<TransactionAudit> findByPreviousStatus(TransactionStatus previousStatus) {
        return transactionAuditRepository.findByPreviousStatus(previousStatus);
    }

    public Page<TransactionAudit> findByNewStatus(TransactionStatus status, Pageable pageable) {
        return transactionAuditRepository.findByNewStatus(status, pageable);
    }

    public Page<TransactionAudit> findAllByTransactionId(String transactionId, Pageable pageable) {
        return transactionAuditRepository.findAllByTransactionId(transactionId, pageable);
    }

    public Page<TransactionAudit> findAll(Pageable pageable) {
        return transactionAuditRepository.findAll(pageable);
    }

    public TransactionAudit save(TransactionAudit audit) {
        return transactionAuditRepository.save(audit);
    }

    public void delete(TransactionAudit audit) {

        transactionAuditRepository.delete(audit);
    }

    public boolean existsByTransactionId(String transactionId) {
        return transactionAuditRepository.findByTransactionId(transactionId).isPresent();
    }
}
