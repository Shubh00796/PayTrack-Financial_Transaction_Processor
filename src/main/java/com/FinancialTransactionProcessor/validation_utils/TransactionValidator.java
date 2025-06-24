package com.FinancialTransactionProcessor.validation_utils;

import com.FinancialTransactionProcessor.dtos.CreateTransactionDTO;
import com.FinancialTransactionProcessor.service_interfaces.AccountService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

// TransactionValidator.java
@Component
@RequiredArgsConstructor
public class TransactionValidator {
    private final AccountService accountService;

    public void validateCreateRequest(CreateTransactionDTO createTransactionDTO) {
        Objects.requireNonNull(createTransactionDTO, "Transaction request cannot be null");
        validateJsrViolations(createTransactionDTO);
        validateAccountExists(createTransactionDTO.getFromAccountId());
        validateAccountExists(createTransactionDTO.getToAccountId());
        validateAmount(createTransactionDTO.getAmount());
    }

    public void validateJsrViolations(CreateTransactionDTO createTransactionDTO) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<CreateTransactionDTO>> violations = validator.validate(createTransactionDTO);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Validation failed: " + violations);
        }
    }

    public void validateAccountExists(String accountId) {
        if (accountService.getAccountById(accountId) == null) {
            throw new IllegalArgumentException("Account not found: " + accountId);
        }
    }

    public void validateAmount(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount cannot be null");
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }
}