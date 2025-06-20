package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.AccountStatus;
import com.FinancialTransactionProcessor.enums.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponseDTO  {

    @NotNull(message = "User ID is required")
    @NotBlank(message = "User ID cannot be empty")
    @Size(min = 1, max = 50, message = "User ID must be between 1 and 50 characters")
    private String userId;
    @Column(name = "account_id", unique = true, nullable = false, length = 50)
    private String accountId;

    @NotNull(message = "Account type is required")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @NotNull(message = "Currency code is required")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency code must be a 3-letter code in uppercase")
    private String currencyCode;

    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.00", message = "Balance must be greater than or equal to 0.00")
    private BigDecimal balance;

    @NotNull(message = "Available balance is required")
    @DecimalMin(value = "0.00", message = "Available balance must be greater than or equal to 0.00")
    private BigDecimal availableBalance;

    @NotNull(message = "Frozen amount is required")
    @DecimalMin(value = "0.00", message = "Frozen amount must be greater than or equal to 0.00")
    private BigDecimal frozenAmount;

    @NotNull(message = "Daily limit is required")
    @DecimalMin(value = "0.00", message = "Daily limit must be greater than or equal to 0.00")
    private BigDecimal dailyLimit;

    @NotNull(message = "Monthly limit is required")
    @DecimalMin(value = "0.00", message = "Monthly limit must be greater than or equal to 0.00")
    private BigDecimal monthlyLimit;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
}
