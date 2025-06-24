package com.FinancialTransactionProcessor.service_interfaces;


import com.FinancialTransactionProcessor.dtos.CreateTransactionAuditDTO;
import com.FinancialTransactionProcessor.dtos.TransactionAuditResponseDTO;
import com.FinancialTransactionProcessor.dtos.UpdateTransactionAuditDTO;
import com.FinancialTransactionProcessor.entities.TransactionAudit;
import com.FinancialTransactionProcessor.enums.TransactionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionAuditService {

    TransactionAuditResponseDTO createTransactionAudit(CreateTransactionAuditDTO createTransactionAuditDTO);

    TransactionAuditResponseDTO getTransactionAuditById(String transactionAuditId);

    Page<TransactionAuditResponseDTO> getAllTransactionAudits(Pageable pageable);


    Page<TransactionAuditResponseDTO> getTransactionAuditsByStatus(TransactionStatus status, Pageable pageable);

    Page<TransactionAuditResponseDTO> getTransactionAuditsByTransactionId(String transactionId, Pageable pageable);

    TransactionAuditResponseDTO updateTransactionAudit(String transactionAuditId, UpdateTransactionAuditDTO updateTransactionAuditDTO);



    void deleteTransactionAudit(String transactionAuditId);

    void updateTransactionAuditStatus(String transactionAuditId, TransactionStatus status);

    boolean isTransactionAuditPending(String transactionAuditId);

    boolean isTransactionAuditCompleted(String transactionAuditId);
}
