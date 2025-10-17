package com.FinancialTransactionProcessor.validation_utils;

import com.FinancialTransactionProcessor.dtos.CreateAccountDTO;
import com.FinancialTransactionProcessor.entities.Account;
import com.FinancialTransactionProcessor.enums.AccountStatus;
import com.FinancialTransactionProcessor.service_interfaces.UserService;
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
public class AccountValidator {

    private final UserService userService;

    public void validateCreateRequest(CreateAccountDTO requestDto) {
        Objects.requireNonNull(requestDto, "Request cannot be null");
        validateJsrViolations(requestDto);
        validateUserExists(requestDto.getUserId());
    }


    public void validateUserExists(String userId) {
        validateUser(userId);
    }

    private void validateUser(String userId) {
        if (userService.getUserById(userId) == null) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
    }

    public void validateJsrViolations(CreateAccountDTO dto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<CreateAccountDTO>> violations = validator.validate(dto);
        checkViolations(violations);
    }

    private static void checkViolations(Set<ConstraintViolation<CreateAccountDTO>> violations) {
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Validation failed: " + violations);
        }
    }

    public boolean isInvalidAmount(BigDecimal amount) {
        return amount == null || amount.signum() <= 0;
    }

    public void validateAmount(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount cannot be null");
        checkAmount(amount);
    }

    private static void checkAmount(BigDecimal amount) {
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
    }

    public void validateLimit(BigDecimal limit) {
        Objects.requireNonNull(limit, "Limit cannot be null");
        checkNonNegative(limit);
    }

    private static void checkNonNegative(BigDecimal limit) {
        if (limit.signum() < 0) {
            throw new IllegalArgumentException("Limit must be non-negative");
        }
    }

    public void validateAccountStatus(Account account) {
        checkActiveStatus(account);
    }

    private static void checkActiveStatus(Account account) {
        if (account.getStatus() == AccountStatus.CLOSED || account.getStatus() == AccountStatus.FROZEN) {
            throw new IllegalStateException("Account is not active");
        }
    }
}
