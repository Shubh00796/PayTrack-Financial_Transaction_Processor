package com.FinancialTransactionProcessor.service_impls;

import com.FinancialTransactionProcessor.dtos.*;
import com.FinancialTransactionProcessor.entities.TransactionAudit;
import com.FinancialTransactionProcessor.enums.TransactionStatus;
import com.FinancialTransactionProcessor.exceptions_handling.ResourceNotFoundException;
import com.FinancialTransactionProcessor.mappers.TransactionAuditMapper;
import com.FinancialTransactionProcessor.reposiotry_services.TransactionAuditRepoService;
import com.FinancialTransactionProcessor.service_interfaces.TransactionAuditService;
import com.FinancialTransactionProcessor.service_interfaces.TransactionQueryService;
import com.FinancialTransactionProcessor.validation_utils.TransactionAuditValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionAuditServiceImpl implements TransactionAuditService {

    private final TransactionAuditRepoService repoService;
    private final TransactionAuditMapper mapper;
    private final TransactionAuditValidator validator;
    private final TransactionQueryService transactionService;

    @Override
    public TransactionAuditResponseDTO createTransactionAudit(CreateTransactionAuditDTO dto) {
        validator.validateCreateRequest(dto);

        TransactionResponseDTO transaction = getValidatedTransaction(dto.getTransactionId());
        TransactionAudit audit = prepareAuditEntity(dto, transaction);

        TransactionAudit savedAudit = repoService.save(audit);
        log.info("Created transaction audit for transactionId: {}", dto.getTransactionId());

        return mapper.toDto(savedAudit);
    }

    @Override
    public TransactionAuditResponseDTO getTransactionAuditById(String auditId) {
        return mapper.toDto(getAuditOrThrow(auditId));
    }

    @Override
    public Page<TransactionAuditResponseDTO> getAllTransactionAudits(Pageable pageable) {
        return repoService.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public Page<TransactionAuditResponseDTO> getTransactionAuditsByStatus(TransactionStatus status, Pageable pageable) {
        return repoService.findByNewStatus(status, pageable).map(mapper::toDto);
    }

    @Override
    public Page<TransactionAuditResponseDTO> getTransactionAuditsByTransactionId(String transactionId, Pageable pageable) {
        return repoService.findAllByTransactionId(transactionId, pageable).map(mapper::toDto);
    }

    @Override
    public TransactionAuditResponseDTO updateTransactionAudit(String auditId, UpdateTransactionAuditDTO dto) {
        validator.validateUpdateRequest(dto);

        TransactionAudit audit = getAuditOrThrow(auditId);
        mapper.updateEntity(audit, dto);

        TransactionAudit updatedAudit = repoService.save(audit);
        log.info("Updated transaction audit with ID: {}", auditId);

        return mapper.toDto(updatedAudit);
    }

    @Override
    public void deleteTransactionAudit(String auditId) {
        TransactionAudit audit = getAuditOrThrow(auditId);
        repoService.delete(audit);
        log.info("Deleted transaction audit with ID: {}", auditId);
    }

    @Override
    public void updateTransactionAuditStatus(String auditId, TransactionStatus status) {
        TransactionAudit audit = getAuditOrThrow(auditId);
        audit.setNewStatus(status);
        repoService.save(audit);
        log.info("Updated status of transaction audit ID: {} to {}", auditId, status);
    }

    @Override
    public boolean isTransactionAuditPending(String auditId) {
        return getAuditOrThrow(auditId).getNewStatus() == TransactionStatus.PENDING;
    }

    @Override
    public boolean isTransactionAuditCompleted(String auditId) {
        return getAuditOrThrow(auditId).getNewStatus() == TransactionStatus.COMPLETED;
    }

    // 🔒 Private helper methods

    private TransactionAudit getAuditOrThrow(String auditId) {
        return Optional.ofNullable(repoService.findByTransactionId(auditId))
                .orElseThrow(() -> new ResourceNotFoundException("Transaction audit not found for ID: " + auditId));
    }

    private TransactionResponseDTO getValidatedTransaction(String transactionId) {
        return Optional.ofNullable(transactionService.getTransactionById(transactionId))
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found for ID: " + transactionId));
    }

    private TransactionAudit prepareAuditEntity(CreateTransactionAuditDTO dto, TransactionResponseDTO transaction) {
        TransactionAudit audit = mapper.toEntity(dto);
        audit.setPreviousStatus(transaction.getStatus());
        audit.setNewStatus(dto.getNewStatus());
        return audit;
    }
}
