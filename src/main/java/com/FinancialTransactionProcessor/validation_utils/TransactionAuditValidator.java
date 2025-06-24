package com.FinancialTransactionProcessor.validation_utils;

import com.FinancialTransactionProcessor.dtos.CreateTransactionAuditDTO;
import com.FinancialTransactionProcessor.dtos.UpdateTransactionAuditDTO;
import com.FinancialTransactionProcessor.enums.TransactionStatus;
import com.FinancialTransactionProcessor.exceptions_handling.ResourceNotFoundException;
import com.FinancialTransactionProcessor.service_interfaces.TransactionQueryService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TransactionAuditValidator {

    private final TransactionQueryService transactionService;

    public void validateCreateRequest(CreateTransactionAuditDTO dto) {
        Objects.requireNonNull(dto, "CreateTransactionAuditDTO cannot be null");
        validateJsrViolations(dto);
        validateTransactionExists(dto.getTransactionId());
        Objects.requireNonNull(dto.getChangedBy(), "ChangedBy cannot be null");
        Objects.requireNonNull(dto.getPreviousStatus(), "Previous status cannot be null");
        Objects.requireNonNull(dto.getNewStatus(), "New status cannot be null");
        validateStatusTransition(dto.getPreviousStatus(), dto.getNewStatus());
    }

    public void validateUpdateRequest(UpdateTransactionAuditDTO dto) {
        Objects.requireNonNull(dto, "UpdateTransactionAuditDTO cannot be null");
        validateJsrViolations(dto);
        Objects.requireNonNull(dto.getNewStatus(), "New status cannot be null");
    }

    private <T> void validateJsrViolations(T dto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Validation failed: " + violations);
        }
    }

    public void validateTransactionExists(String transactionId) {
        if (transactionService.getTransactionById(transactionId) == null) {
            throw new ResourceNotFoundException("Transaction not found: " + transactionId);
        }
    }

    private void validateStatusTransition(TransactionStatus previous, TransactionStatus next) {
        if (previous == next) {
            throw new IllegalArgumentException("New status must be different from previous status");
        }
    }
}
