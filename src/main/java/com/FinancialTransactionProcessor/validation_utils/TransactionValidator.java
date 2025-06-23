package com.FinancialTransactionProcessor.validation_utils;

import com.FinancialTransactionProcessor.dtos.CreateTransactionDTO;
import com.FinancialTransactionProcessor.entities.Account;
import com.FinancialTransactionProcessor.enums.AccountStatus;
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

@Component
@RequiredArgsConstructor
public class TransactionValidator {

    private final AccountService accountService;

    public void validateCreateRequest(CreateTransactionDTO dto) {
        Objects.requireNonNull(dto, "Transaction request cannot be null");
        validateJsrViolations(dto);
        validateAccountExists(dto.getFromAccountId());
        validateAccountExists(dto.getToAccountId());
        validateAmount(dto.getAmount());
    }

    public void validateJsrViolations(CreateTransactionDTO dto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<CreateTransactionDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Validation failed: " + violations);
        }
    }

    public void validateAccountExists(String accountId) {
        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountId);
        }
        validateAccountStatus(account);
    }

    public void validateAccountStatus(Account account) {
        if (account.getStatus() == AccountStatus.CLOSED || account.getStatus() == AccountStatus.FROZEN) {
            throw new IllegalStateException("Account is not active: " + account.getId());
        }
    }

    public void validateAmount(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount cannot be null");
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }
}
