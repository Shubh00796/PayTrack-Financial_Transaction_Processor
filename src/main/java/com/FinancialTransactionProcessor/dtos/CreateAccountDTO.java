package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.AccountStatus;
import com.FinancialTransactionProcessor.enums.AccountType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountDTO {

    @NotNull(message = "User ID is required")
    @NotBlank(message = "User ID cannot be empty")
    @Size(min = 1, max = 50, message = "User ID must be between 1 and 50 characters")
    private String userId;

    @NotNull(message = "Account ID is required")
    @NotBlank(message = "Account ID cannot be empty")
    @Size(min = 1, max = 50, message = "Account ID must be between 1 and 50 characters")
    private String accountId;

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @NotNull(message = "Currency code is required")
    @NotBlank(message = "Currency code cannot be empty")
    @Size(min = 3, max = 3, message = "Currency code must be 3 characters long")
    private String currencyCode;

    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.00", message = "Balance must be greater than or equal to 0.00")
    @Digits(integer = 15, fraction = 2, message = "Balance must be a valid decimal number")
    private BigDecimal balance;

    @NotNull(message = "Available balance is required")
    @DecimalMin(value = "0.00", message = "Available balance must be greater than or equal to 0.00")
    @Digits(integer = 15, fraction = 2, message = "Available balance must be a valid decimal number")
    private BigDecimal availableBalance;

    @DecimalMin(value = "0.00", message = "Frozen amount must be greater than or equal to 0.00")
    @Digits(integer = 15, fraction = 2, message = "Frozen amount must be a valid decimal number")
    private BigDecimal frozenAmount;

    @DecimalMin(value = "0.00", message = "Daily limit must be greater than or equal to 0.00")
    @Digits(integer = 15, fraction = 2, message = "Daily limit must be a valid decimal number")
    private BigDecimal dailyLimit;

    @DecimalMin(value = "0.00", message = "Monthly limit must be greater than or equal to 0.00")
    @Digits(integer = 15, fraction = 2, message = "Monthly limit must be a valid decimal number")
    private BigDecimal monthlyLimit;

    @NotNull(message = "Status is required")
    private AccountStatus status;

    @PastOrPresent(message = "CreatedAt cannot be in the future")
    @CreatedDate
    private LocalDateTime createdAt;

    @PastOrPresent(message = "UpdatedAt cannot be in the future")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
