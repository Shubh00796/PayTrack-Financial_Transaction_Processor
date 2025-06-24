package com.FinancialTransactionProcessor.validation_utils;

import com.FinancialTransactionProcessor.dtos.CreateFraudAlertDTO;
import com.FinancialTransactionProcessor.dtos.UpdateFraudAlertDTO;
import com.FinancialTransactionProcessor.service_interfaces.TransactionQueryService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class FraudAlertValidator {

    private final TransactionQueryService transactionQueryService;

    public void validateCreateRequest(CreateFraudAlertDTO dto) {
        Objects.requireNonNull(dto, "CreateFraudAlertDTO cannot be null");
        validateJsrViolations(dto);
        validateTransactionExists(dto.getTransactionId());
        validateRiskScore(dto.getRiskScore());
        Objects.requireNonNull(dto.getRuleId(), "Rule ID cannot be null");
        Objects.requireNonNull(dto.getAlertType(), "Alert type cannot be null");
    }

    public void validateUpdateRequest(UpdateFraudAlertDTO dto) {
        Objects.requireNonNull(dto, "UpdateFraudAlertDTO cannot be null");
        validateJsrViolations(dto);
        // Add more field validations if needed
    }

    private <T> void validateJsrViolations(T dto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Validation failed: " + violations);
        }
    }

    private void validateTransactionExists(String transactionId) {
        if (transactionQueryService.getTransactionById(transactionId) == null) {
            throw new IllegalArgumentException("Transaction not found: " + transactionId);
        }
    }

    private void validateRiskScore(Integer riskScore) {
        Objects.requireNonNull(riskScore, "Risk score cannot be null");
        if (riskScore < 0 || riskScore > 100) {
            throw new IllegalArgumentException("Risk score must be between 0 and 100");
        }
    }
}
