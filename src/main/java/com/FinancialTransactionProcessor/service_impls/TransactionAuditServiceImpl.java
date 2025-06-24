package com.FinancialTransactionProcessor.service_impls;

import com.FinancialTransactionProcessor.dtos.CreateTransactionAuditDTO;
import com.FinancialTransactionProcessor.dtos.TransactionAuditResponseDTO;
import com.FinancialTransactionProcessor.dtos.TransactionResponseDTO;
import com.FinancialTransactionProcessor.dtos.UpdateTransactionAuditDTO;
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

        TransactionResponseDTO transaction = validateTransactionId(dto);

        TransactionAudit audit = mapper.toEntity(dto);
        audit.setPreviousStatus(transaction.getStatus());
        audit.setNewStatus(dto.getNewStatus());

        TransactionAudit savedAudit = repoService.save(audit);
        log.info("Created transaction audit for transactionId: {}", dto.getTransactionId());

        return mapper.toDto(savedAudit);
    }



    @Override
    public TransactionAuditResponseDTO getTransactionAuditById(String transactionAuditId) {
        TransactionAudit audit = getAuditOrThrow(transactionAuditId);
        return mapper.toDto(audit);
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
    public TransactionAuditResponseDTO updateTransactionAudit(String transactionAuditId, UpdateTransactionAuditDTO dto) {
        validator.validateUpdateRequest(dto);

        TransactionAudit audit = getAuditOrThrow(transactionAuditId);
        mapper.updateEntity(audit, dto);

        TransactionAudit updated = repoService.save(audit);
        log.info("Updated transaction audit with ID: {}", transactionAuditId);

        return mapper.toDto(updated);
    }


    @Override
    public void deleteTransactionAudit(String transactionAuditId) {
        TransactionAudit audit = getAuditOrThrow(transactionAuditId);
        repoService.delete(audit);
        log.info("Deleted transaction audit with ID: {}", transactionAuditId);
    }

    @Override
    public void updateTransactionAuditStatus(String transactionAuditId, TransactionStatus status) {
        TransactionAudit audit = getAuditOrThrow(transactionAuditId);
        audit.setNewStatus(status);
        repoService.save(audit);
        log.info("Updated status of transaction audit ID: {} to {}", transactionAuditId, status);
    }

    @Override
    public boolean isTransactionAuditPending(String transactionAuditId) {
        return getAuditOrThrow(transactionAuditId).getNewStatus() == TransactionStatus.PENDING;
    }

    @Override
    public boolean isTransactionAuditCompleted(String transactionAuditId) {
        return getAuditOrThrow(transactionAuditId).getNewStatus() == TransactionStatus.COMPLETED;
    }

    // 🔒 Private helper method to reduce duplication
    private TransactionAudit getAuditOrThrow(String transactionAuditId) {
        return Optional.ofNullable(repoService.findByTransactionId(transactionAuditId))
                .orElseThrow(() -> new ResourceNotFoundException("Transaction audit not found for ID: " + transactionAuditId));
    }

    private TransactionResponseDTO validateTransactionId(CreateTransactionAuditDTO dto) {
        TransactionResponseDTO transaction = Optional.ofNullable(
                        transactionService.getTransactionById(dto.getTransactionId()))
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found for ID: " + dto.getTransactionId()));
        return transaction;
    }
}
